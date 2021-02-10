package com.project.nutricipe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.nutricipe.bean.UserBean;

@Repository
public interface UserRepo extends JpaRepository<UserBean, Integer> {
	
	UserBean findUserByUsernameAndPassword(String username, String password);
	UserBean findByUsername(String username);
	UserBean findByEmail(String email);

}