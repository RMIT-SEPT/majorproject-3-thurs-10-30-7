import React, { Component } from 'react';
import { connect } from 'react-redux';
import { withRouter } from 'react-router-dom';
import { Redirect } from 'react-router-dom';
import classes from './EditProfile.module.css';

import * as actions from '../../../store/actions/actions';
import Spinner from '../../../components/UI/Spinner/Spinner';
import Button from '../../../components/UI/Button/Button';
import Input from '../../../components/UI/Input/Input';
import ErrorMessage from '../../../components/UI/Input/ErrorMessage';
import { checkValidity, errorMessageToDisplay } from '../../../utility/utility';

export class EditProfile extends Component {
  state = {
    controls: {
      firstName: {
        labelName: 'First Name',
        elementType: 'input',
        elementConfig: {
          type: 'text',
          placeholder: 'Firstname',
        },
        value:
          this.props.profileDetails == null
            ? ''
            : this.props.profileDetails.firstName,
        validation: {
          required: true,
          isLetters: true,
        },
        valid: true,
        touched: false,
      },
      lastName: {
        labelName: 'Last Name',
        elementType: 'input',
        elementConfig: {
          type: 'text',
          placeholder: 'Lastname',
        },
        value:
          this.props.profileDetails == null
            ? ''
            : this.props.profileDetails.lastName,
        validation: {
          required: true,
          isLetters: true,
        },
        valid: true,
        touched: false,
      },
      email: {
        labelName: 'Email',
        elementType: 'input',
        elementConfig: {
          type: 'email',
          placeholder: 'Email',
        },
        value:
          this.props.profileDetails == null
            ? ''
            : this.props.profileDetails.email,
        validation: {
          required: true,
          isEmail: true,
        },
        valid: true,
        touched: false,
      },
      address: {
        labelName: 'Address',
        elementType: 'input',
        elementConfig: {
          type: 'text',
          placeholder: 'Address',
        },
        value:
          this.props.profileDetails == null
            ? ''
            : this.props.profileDetails.address,
        validation: {
          required: true,
        },
        valid: true,
        touched: false,
      },
      phoneNumber: {
        labelName: 'Phone number',
        elementType: 'input',
        elementConfig: {
          type: 'text',
          placeholder: 'Phonenumber',
        },
        value:
          this.props.profileDetails == null
            ? ''
            : this.props.profileDetails.phoneNo,
        validation: {
          required: true,
          isNumeric: true,
          exactLength: 10,
        },
        valid: true,
        touched: false,
      },
      avatar: {
        labelName: 'Avatar',
        elementType: 'input',
        elementConfig: {
          type: 'text',
          placeholder: 'Paste image url here',
        },
        value:
          this.props.profileDetails == null
            ? ''
            : this.props.profileDetails.img,
        validation: {
          required: false,
        },
        valid: true,
        touched: false,
      },
    },
    isFormValid: true,
  };

  componentDidMount() {
    this.props.onFetchProfile(this.props.token);
  }

  // Updates value and validity of input in state when interacted with by the user
  inputChangedHandler = (event, controlName) => {
    const updatedControls = {
      ...this.state.controls,
      [controlName]: {
        ...this.state.controls[controlName],
        value: event.target.value,
        valid: checkValidity(
          event.target.value,
          this.state.controls[controlName].validation
        ),
        touched: true,
      },
    };

    let formIsValid = true;
    for (let identifier in updatedControls) {
      formIsValid = updatedControls[identifier].valid && formIsValid;
    }

    this.setState({ isFormValid: formIsValid, controls: updatedControls });
  };

  // Runs when submitted and calls the redux action
  editProfileHandler = (event) => {
    event.preventDefault();

    if (!this.state.isFormValid) {
      return false;
    }

    let formData = {
      firstName: this.state.controls.firstName.value,
      lastName: this.state.controls.lastName.value,
      email: this.state.controls.email.value,
      phoneNo: this.state.controls.phoneNumber.value,
      address: this.state.controls.address.value,
      img:
        this.state.controls.avatar.value === ''
          ? 'https://i.imgur.com/Eie9ARV.png'
          : this.state.controls.avatar.value,
    };

    this.props.onEditProfile(formData, this.props.token, this.props.history);
  };

  render() {
    let form = null;

    const formElementsArray = [];
    for (let key in this.state.controls) {
      formElementsArray.push({
        id: key,
        config: this.state.controls[key],
      });
    }

    // Creates an input element with configurations from state
    let formElements = formElementsArray.map((formElement) => (
      <React.Fragment key={formElement.id}>
        <Input
          key={formElement.id}
          label={formElement.config.labelName}
          elementType={formElement.config.elementType}
          elementConfig={formElement.config.elementConfig}
          value={formElement.config.value}
          changed={(event) => this.inputChangedHandler(event, formElement.id)}
          invalid={!formElement.config.valid}
          shouldValidate={formElement.config.validation}
          touched={formElement.config.touched}
        />
        {formElement.config.valid ? null : (
          <ErrorMessage
            key={formElement.id + 'Error'}
            message={errorMessageToDisplay(formElement.id)}
          />
        )}
      </React.Fragment>
    ));

    // Renders error message if there is any errors
    let errorMessage = null;
    if (this.props.error) {
      errorMessage = <p>{this.props.error}</p>;
    }

    // Redirects user if already logged in
    let authRedirect = null;
    if (this.props.isAuthenticated) {
      authRedirect = <Redirect to={this.props.authRedirectPath} />;
    }

    //displays error message beneath the submit button
    let errorMsg = null;
    if (!this.state.isFormValid) {
      errorMsg = (
        <p className='text-danger'>
          Please make sure all fields are filled and valid.
        </p>
      );
    }

    form = (
      <form onSubmit={this.editProfileHandler}>
        <div className='form-group'>
          {formElements}
          {errorMessage}
          <Button disabled={!this.state.isFormValid} classes='btn btn-primary'>
            Submit
          </Button>
          {errorMsg}
        </div>
      </form>
    );

    // Renders a spinning icon if loading
    if (this.props.loading) {
      form = <Spinner />;
    }

    return (
      <div className={classes.EditProfileBox}>
        {authRedirect}
        <h1>Edit Profile</h1>
        {form}
      </div>
    );
  }
}

const mapStateToProps = (state) => {
  return {
    loading: state.profile.loading,
    error: state.profile.error,
    profileDetails: state.profile.profileDetails,
    token: state.auth.token,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    onEditProfile: (formData, token, history) =>
      dispatch(actions.editProfile(formData, token, history)),
    onFetchProfile: (token) => dispatch(actions.fetchProfile(token)),
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(withRouter(EditProfile));
