package com.project.nutricipe.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.project.nutricipe.bean.DietBean;
import com.project.nutricipe.bean.FridgeBean;
import com.project.nutricipe.bean.RoleBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.repo.DietRepo;
import com.project.nutricipe.repo.FridgeRepo;
import com.project.nutricipe.repo.RoleRepo;
import com.project.nutricipe.repo.UserRepo;
import com.project.nutricipe.security.WebSecurityConfig;



@Controller
public class LoginController {
	@Autowired
	private PasswordEncoder passwordEncoder;
	private UserRepo userRepo;
	private WebSecurityConfig webSecurityConfig;
	private List<UserBean> foundUsers;
	private RoleRepo roleRepo;
	private DietRepo dietRepo;
	private FridgeRepo fridgeRepo;
	
	public LoginController(UserRepo userRepo, WebSecurityConfig webSecurityConfig, RoleRepo roleRepo, DietRepo dietRepo) {
		this.userRepo = userRepo;
		this.webSecurityConfig = webSecurityConfig;
		this.roleRepo = roleRepo;
		this.dietRepo = dietRepo;
	}
	@PostMapping(path="/register")
	
	public ModelAndView register(@RequestParam(value="email")String email, @RequestParam(value="username")String username,
			@RequestParam(value="password")String password, @RequestParam(value="repeatPassword")String repeatPassword, @RequestParam(value="diet")String diet_Id, HttpServletRequest request) {
		 boolean usernameExists = false;
		 boolean emailExists = false;
			int dietId = 0;
		 if(tryParseInt(diet_Id)) {
			 dietId = Integer.parseInt(diet_Id);
		 }
		 
		if(password.equals(repeatPassword)) {
			UserBean user = new UserBean(username.trim(),passwordEncoder.encode(password),email.trim().toLowerCase());
			Set<RoleBean> roles = new HashSet<RoleBean>();
			RoleBean foundRole = roleRepo.findRoleByCode("ROLE_USER");
			if(foundRole == null) {
				RoleBean role = new RoleBean();
				role.setCode("ROLE_USER");
				roles.add(role);
				RoleBean role2 = new RoleBean();
				role2.setCode("ROLE_ADMIN");
				roles.add(role2);
			}
			else {
				roles.add(foundRole);
			}
			user.setRoles(roles);
			foundUsers = userRepo.findAll();
			for(UserBean foundUser : foundUsers) {
				if(foundUser.getUsername().equals(username.trim())) {
					usernameExists = true;
				}
				if(foundUser.getEmail().equals(email.trim().toLowerCase())) {
					emailExists = true;
				}
			}
			if(usernameExists || emailExists) {
				ModelAndView model = new ModelAndView();
				model.addObject("usernameExists", usernameExists);
				model.addObject("emailExists",emailExists);
				model.setViewName("register.html");
				return model;
			}
			else {
				if(dietId!=0) {
					
				
				Optional<DietBean> diet = dietRepo.findById(dietId);
				if(diet.isPresent()) {
					user.setDiet(diet.get());
				}
				}
				FridgeBean fridge = new FridgeBean();
				//fridgeRepo.saveAndFlush(fridge);
				user.setFridge(fridge);
				userRepo.saveAndFlush(user);
				ModelAndView model = new ModelAndView("redirect:/login");
				model.addObject("user",user);
				return model;
			
			}
			
			//ModelAndView model = new ModelAndView("redirect:/home.html");
		
			
		}
		return null;
	
		
	}
	/*@PostMapping(path="/login")
	public String login(@RequestParam(value="username")String username, 
			@RequestParam(value="password")String password, 
			HttpSession session) {
		UserBean user = userRepo.findUserByUsernameAndPassword(username,password);
		if(user != null) {
			session.setAttribute("user", user);
			try {
				UserDetails userDetails = webSecurityConfig.userDetailsServiceBean().loadUserByUsername(user.getUsername());
				if(userDetails!=null) {
					Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),userDetails.getPassword(),userDetails.getAuthorities());
					
					SecurityContextHolder.getContext().setAuthentication(auth);
					ServletRequestAttributes attr = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes(); 
					HttpSession http = attr.getRequest().getSession(true);
					http.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
					
					
				}
				return "home.html";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
			return "login.html";
		
		
	
	}*/
	@GetMapping(path="/login")
	public ModelAndView login() {
		ModelAndView model = new ModelAndView();
		model.setViewName("login.html");
		return model;
		
	}
	@GetMapping(path="/register")
	public ModelAndView register(ModelAndView model) {
		if (model==null) {
		model = new ModelAndView();
		}
		model.setViewName("register.html");
		return model;
		
	}
	@GetMapping(path="/home")
	public ModelAndView home(ModelAndView model) {
		if (model==null) {
		model = new ModelAndView();
		}
		model.setViewName("home.html");
		return model;
		
	}
	boolean tryParseInt(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	      } catch (NumberFormatException e) {  
	         return false;  
	      }  
	}

}
