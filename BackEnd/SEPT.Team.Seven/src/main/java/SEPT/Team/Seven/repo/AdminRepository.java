package SEPT.Team.Seven.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

import SEPT.Team.Seven.model.Admin;

@CrossOrigin(origins = "http://d2maztjzxux6f3.cloudfront.net")
public interface AdminRepository extends JpaRepository<Admin, Integer>{

}