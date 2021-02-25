package com.project.nutricipe.controller.user;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.nutricipe.bean.RoleBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.controller.UserController;
import com.project.nutricipe.repo.DietRepo;
import com.project.nutricipe.repo.RoleRepo;
import com.project.nutricipe.repo.UserRepo;
import com.project.nutricipe.security.UserPrincipal;

@RunWith(Parameterized.class)
public class AddUserTest {
	@Parameters(name = "{index}: with email = {0}, username = {1}, password = {2}, repeatPassword = {3}, dietId = {4}, loggedUser = {5}, role= {6} and expected result={7}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { //
				{ null, null, null, null, null, null, null, "Input parameters cant be null!" },
				{ "email5@mail.com", "username5", "pass", "pass", "0",
						new UserBean("username", "password", "email@email.com"), "ROLE_ADMIN",
						"User has been successfully created!" },
				{ "email5@mail.com", "username5", "pass", "pass2", "0",
						new UserBean("username", "password", "email@email.com"), "ROLE_ADMIN",
						"Error: Password mismatch!" },
				{ "email2@email.com", "username5", "pass", "pass", "0",
						new UserBean("username", "password", "email@email.com"), "ROLE_ADMIN", "Error: Email exists!" },
				{ "email5@mail.com", "name", "pass", "pass", "0",
						new UserBean("username", "password", "email@email.com"), "ROLE_ADMIN",
						"Error: Username exists!" },
				{ "email5@mail.com", "name", "pass", "pass", "0",
						new UserBean("username", "password", "email@email.com"), "ROLE_USER", "Error: Forbidden!" }, });
	}

	@Parameter(0)
	public String email;
	@Parameter(1)
	public String username;
	@Parameter(2)
	public String password;
	@Parameter(3)
	public String repeatPassword;
	@Parameter(4)
	public String dietId;
	@Parameter(5)
	public UserBean loggedUser;
	@Parameter(6)
	public String roleCode;
	@Parameter(7)
	public String expectedResult;
	private UserController userController;
	private UserRepo userRepo;
	private UserPrincipal principal;
	private RoleRepo roleRepo;
	// private DietRepo dietRepo= new DietRepo();
	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	@Rule
	public ErrorCollector collector = new ErrorCollector();

	@Before
	public void setup() {
		RoleBean role = new RoleBean();
		role.setCode(roleCode);
		role.setId(1);
		Set<RoleBean> roles = new HashSet<>(Arrays.asList(role));
		roleRepo = mock(RoleRepo.class);
		userRepo = mock(UserRepo.class);
		if (loggedUser != null) {
			loggedUser.setRoles(roles);
			principal = new UserPrincipal(loggedUser, roles);
		} else {
			principal = null;
		}
		UserBean user1 = new UserBean();
		// user1.setId(1);
		user1.setUsername("name");
		user1.setPassword("pass");
		user1.setEmail("email2@email.com");
		UserBean user2 = new UserBean();
		// user2.setId(3);
		user2.setUsername("name2");
		user2.setPassword("pass2");
		user2.setEmail("email3@email.com");
		List<UserBean> users = new ArrayList<UserBean>();
		users.add(user1);
		users.add(user2);
		doReturn(users).when(userRepo).findAll();
		doReturn(role).when(roleRepo).findRoleByCode("ROLE_USER");
		userController = new UserController(userRepo, null, roleRepo, null, encoder);
	}

	@Test
	public void testAddUser() {
		final List<String> result = userController.addUser(email, username, password, repeatPassword, dietId,
				principal);
		collector.checkThat(result.get(0), IsEqual.equalTo(expectedResult));

	}


}
