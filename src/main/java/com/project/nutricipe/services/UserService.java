package com.project.nutricipe.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.nutricipe.bean.DietBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.repo.DietRepo;
import com.project.nutricipe.repo.FridgeRepo;
import com.project.nutricipe.repo.RoleRepo;
import com.project.nutricipe.repo.UserRepo;
import com.project.nutricipe.security.WebSecurityConfig;

@Service
public class UserService {
	@Autowired
	private static PasswordEncoder passwordEncoder;
	@Autowired
	private static UserRepo userRepo;
	@Autowired
	private static WebSecurityConfig webSecurityConfig;
	@Autowired
	private static List<UserBean> foundUsers;
	@Autowired
	private static RoleRepo roleRepo;
	@Autowired
	private static DietRepo dietRepo;
	@Autowired
	private static FridgeRepo fridgeRepo;
	
	public UserService (UserRepo userRepo, WebSecurityConfig webSecurityConfig, RoleRepo roleRepo, DietRepo dietRepo,
			PasswordEncoder passwordEncoder,FridgeRepo fridgeRepo) {
		this.userRepo = userRepo;
		this.webSecurityConfig = webSecurityConfig;
		this.roleRepo = roleRepo;
		this.dietRepo = dietRepo;
		this.passwordEncoder = passwordEncoder;
		this.fridgeRepo = fridgeRepo;
	}
	public static ResponseEntity<List<UserBean>> getUserBySearchString(String query) {
		List<UserBean> allUsers = userRepo.findAll();
		for (UserBean user : allUsers) {
			//System.out.println("Recipe:");
		
			//System.out.println(recipe.getName());
		}

		List<UserBean> result = new ArrayList<UserBean>();
		for(UserBean user : allUsers) {
			if(user.getUsername().toLowerCase().contains(query.toLowerCase())) {
				result.add(user);
				System.out.println("Result User:");
				
				System.out.println(user.getUsername());
				
			}
		}
		if(result.size()>0) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	

}
