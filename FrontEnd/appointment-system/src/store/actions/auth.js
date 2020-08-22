import * as actionTypes from "./actionTypes";
import axios from "axios";
import jwtDecode from "jwt-decode";

export const authStart = () => {
  return {
    type: actionTypes.AUTH_START,
  };
};

export const authSuccess = (token, userId) => {
  return {
    type: actionTypes.AUTH_SUCCESS,
    token: token,
    userId: userId,
  };
};

export const authFail = (error) => {
  return {
    type: actionTypes.AUTH_FAIL,
    error: error,
  };
};

export const logout = () => {
  localStorage.removeItem("token");
  localStorage.removeItem("userId");
  localStorage.removeItem("expirationDate");
  return {
    type: actionTypes.AUTH_LOGOUT,
  };
};

export const checkAuthTimeout = (expirationTime) => {
  return (dispatch) => {
    setTimeout(() => {
      dispatch(logout());
    }, expirationTime * 1000);
  };
};

export const auth = (username, password, isSignUp) => {
  return (dispatch) => {
    dispatch(authStart());

    const userDetails = {
      username: username,
      password: password,
    };

    console.log(userDetails);

    let authCode = "";

    axios
      .post("http://localhost:8080/users/signin", userDetails)
      .then((response) => {
        authCode = response.data;
        let decodedJwt = jwtDecode(authCode);

        const userId = decodedJwt.userId;
        const expirationDate = new Date();
        const secondsToExpire = decodedJwt.exp - decodedJwt.iat;
        expirationDate.setMinutes(expirationDate.getMinutes() + secondsToExpire / 60);

        console.log(expirationDate);
        localStorage.setItem("token", response.data);
        localStorage.setItem("expirationDate", expirationDate);
        localStorage.setItem("userId", decodedJwt.userId);

        dispatch(authSuccess(response.data, userId));
        dispatch(checkAuthTimeout(secondsToExpire));

        let url = "";
        switch (decodedJwt.roles[0].authority) {
          case "ROLE_ADMIN":
            url = "admins";
            break;
          case "ROLE_EMPLOYEE":
            url = "employees";
            break;
          case "ROLE_CUSTOMER":
            url = "customers";
            break;
          default:
            return "";
        }

        const config = {
          headers: {
            Authorization: "Bearer " + authCode,
          },
        };

        axios.get("http://localhost:8080/api/" + url + "/" + userId, config).then((response) => console.log(response));
      })
      .catch((error) => {
        dispatch(authFail("Username or password is incorrect."));
      });
  };
};

export const setAuthRedirectPath = (path) => {
  return {
    type: actionTypes.SET_AUTH_REDIRECT_PATH,
    path: path,
  };
};

export const authCheckState = () => {
  return (dispatch) => {
    const token = localStorage.getItem("token");
    if (!token) {
      dispatch(logout());
    } else {
      const expirationDate = new Date(localStorage.getItem("expirationDate"));
      if (expirationDate <= new Date()) {
        dispatch(logout());
      } else {
        const userId = localStorage.getItem("userId");
        dispatch(authSuccess(token, userId));
        // Expiry in seconds by taking the difference
        dispatch(checkAuthTimeout((expirationDate.getTime() - new Date().getTime()) / 1000));
      }
    }
  };
};
