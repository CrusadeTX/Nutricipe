package com.project.nutricipe.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.nutricipe.bean.RoleBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.repo.RoleRepo;
import com.project.nutricipe.repo.UserRepo;
import com.project.nutricipe.security.UserPrincipal;
import com.project.nutricipe.security.WebSecurityConfig;

@RestController
public class UserController {
	@Autowired
	private PasswordEncoder passwordEncoder;
	private UserRepo userRepo;
	private WebSecurityConfig webSecurityConfig;
	private List<UserBean> foundUsers;
	private RoleRepo roleRepo;

	public UserController(UserRepo userRepo, WebSecurityConfig webSecurityConfig, RoleRepo roleRepo) {
		this.userRepo = userRepo;
		this.webSecurityConfig = webSecurityConfig;
		this.roleRepo = roleRepo;
	}

	@GetMapping(path = "/user/all")
	public List<UserBean> getAllUsers(@AuthenticationPrincipal UserPrincipal principal) {
		UserBean user = principal.getLoggedInUser();
		if (user != null) {
			return userRepo.findAll();
		} else {
			return null;
		}
	}

	@GetMapping(path = "/user/current")
	public UserBean getUser(@AuthenticationPrincipal UserPrincipal principal) {
		UserBean user = principal.getLoggedInUser();
		if (user != null) {
			return user;

		} else {

			return null;
		}

	}

	@PostMapping(path = "/user/update")

	public List<String> updateUser(@RequestParam(value = "id") int id, @RequestParam(value = "email") String email,
			@RequestParam(value = "username") String username, @RequestParam(value = "password") String password,
			@RequestParam(value = "repeatPassword") String repeatPassword,
			@AuthenticationPrincipal UserPrincipal principal) {
		UserBean loggedUser = principal.getLoggedInUser();
		UserBean user = loggedUser;
		List<String> result = new ArrayList<String>();
		if (loggedUser.getId() == id) {
			boolean usernameExists = false;
			boolean emailExists = false;
			if (password.equals(repeatPassword)) {
				user.setEmail(email.trim().toLowerCase());
				user.setPassword(passwordEncoder.encode(password));
				user.setUsername(username.trim().toLowerCase());
				foundUsers = userRepo.findAll();
				for (UserBean foundUser : foundUsers) {
					if (foundUser.getUsername().equals(username.trim()) && foundUser.getId() != id) {
						usernameExists = true;
					}
					if (foundUser.getEmail().equals(email.trim().toLowerCase()) && foundUser.getId() != id) {
						emailExists = true;
					}
				}
				if (usernameExists || emailExists) {
					if (usernameExists) {
						result.add("Error Username exists!");
					}
					if (emailExists) {
						result.add("Error Email exists!");
					}
					return result;

				} else {
					userRepo.saveAndFlush(user);

					result.add("User has been successfully updated!");
					return result;
				}
			} else {
				result.add("Error Password mismatch!");
				return result;
			}

		} else {
			result.add("Error Unauthorized!");
			return result;
		}

	}

	@PostMapping(path = "/user/update/admin")

	public List<String> updateUserAsAdmin(@RequestParam(value = "id") int id,
			@RequestParam(value = "email") String email, @RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "repeatPassword") String repeatPassword,
			@AuthenticationPrincipal UserPrincipal principal) {
		UserBean loggedUser = principal.getLoggedInUser();
		List<String> result = new ArrayList<String>();
		if (loggedUser != null) {
			UserBean user = userRepo.getOne(id);

			boolean usernameExists = false;
			boolean emailExists = false;
			if (password.equals(repeatPassword)) {
				user.setEmail(email.trim().toLowerCase());
				user.setPassword(passwordEncoder.encode(password));
				user.setUsername(username.trim().toLowerCase());
				foundUsers = userRepo.findAll();
				for (UserBean foundUser : foundUsers) {
					if (foundUser.getUsername().equals(username.trim()) && foundUser.getId() != id) {
						usernameExists = true;
					}
					if (foundUser.getEmail().equals(email.trim().toLowerCase()) && foundUser.getId() != id) {
						emailExists = true;
					}
				}
				if (usernameExists || emailExists) {
					if (usernameExists) {
						result.add("Error: Username exists!");
					}
					if (emailExists) {
						result.add(" Error: Email exists!");
					}
					return result;

				} else {
					userRepo.saveAndFlush(user);

					result.add("User has been successfully updated!");
					return result;
				}

			} else {
				result.add("Error: Password mismatch!");
				return result;
			}
		} else {
			result.add("Error: Unauthorized!");
			return result;
		}

	}

	@PostMapping(path = "/user/add")

	public List<String> addUser(@RequestParam(value = "email") String email,
			@RequestParam(value = "username") String username, @RequestParam(value = "password") String password,
			@RequestParam(value = "repeatPassword") String repeatPassword,
			@AuthenticationPrincipal UserPrincipal principal) {
		UserBean loggedUser = principal.getLoggedInUser();

		List<String> result = new ArrayList<String>();
		if (loggedUser != null) {
			for (RoleBean givenRole : loggedUser.getRoles()) {
				if (givenRole.getCode().equals("ROLE_ADMIN")) {
					boolean usernameExists = false;
					boolean emailExists = false;
					if (password.equals(repeatPassword)) {
						UserBean user = new UserBean(username.trim(), passwordEncoder.encode(password),
								email.trim().toLowerCase());
						Set<RoleBean> roles = new HashSet<RoleBean>();
						RoleBean foundRole = roleRepo.findRoleByCode("ROLE_USER");
						if (foundRole == null) {
							RoleBean role = new RoleBean();
							role.setCode("ROLE_USER");
							roles.add(role);
						} else {
							roles.add(foundRole);
						}
						user.setRoles(roles);
						foundUsers = userRepo.findAll();
						for (UserBean foundUser : foundUsers) {
							if (foundUser.getUsername().equals(username.trim())) {
								usernameExists = true;
							}
							if (foundUser.getEmail().equals(email.trim().toLowerCase())) {
								emailExists = true;
							}
						}
						if (usernameExists || emailExists) {
							if (usernameExists) {
								result.add("Error: Username exists!");
							}
							if (emailExists) {
								result.add("Error: Email exists!");
							}
							return result;

						} else {
							userRepo.saveAndFlush(user);

							result.add("User has been successfully created!");
							return result;
						}

					} else {
						result.add("Error: Password mismatch!");
						return result;
					}

				} else {
					result.add("Error: Forbidden!");
					return result;
				}

			}
		}
		result.add("Unauthorized!");
		return result;
	}

	@DeleteMapping(path = "/user/deletebyId")
	public ResponseEntity<Boolean> deleteUserById(@RequestParam(value = "id") int id,
			@AuthenticationPrincipal UserPrincipal principal) {
		UserBean loggedUser = principal.getLoggedInUser();
		if (loggedUser == null) {
			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
		}
		Optional<UserBean> optionalUser = userRepo.findById(id);
		if (optionalUser.isPresent()) {
			UserBean user = optionalUser.get();
			userRepo.delete(user);
			return new ResponseEntity<>(true, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
		}

	}

}