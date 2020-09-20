package SEPT.Team.Seven.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import SEPT.Team.Seven.model.User;

@CrossOrigin(origins = "http://d2maztjzxux6f3.cloudfront.net")
public interface UserRepository extends JpaRepository<User,Integer>{
    Optional<User> findByUsername(String userName);

}
