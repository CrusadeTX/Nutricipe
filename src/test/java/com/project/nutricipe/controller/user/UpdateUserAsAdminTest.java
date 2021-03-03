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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.nutricipe.bean.RoleBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.controller.UserController;
import com.project.nutricipe.repo.UserRepo;
import com.project.nutricipe.security.UserPrincipal;

@RunWith(Parameterized.class)
public class UpdateUserAsAdminTest {
	@Parameters(name = "{index}: with id = {0} , email = {1}, username = {2}, password = {3}, repeatPassword = {4}, dietId = {5}, loggedUser = {6}, role= {7} and expected result={8}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { //
				{ 0,null, null, null, null, null, null, "ROLE_USER", "Input parameters cant be null!" },
				{ 1,"email6@mail.com", "username2", "pass3", "pass3", "0",
						new UserBean("username", "password", "email@email.com"), "ROLE_USER",
						"User has been successfully updated!" },
				{ 1,"email6@mail.com", "username2", "pass3", "pass3", "0",
							null, "ROLE_USER",
							"Error: Unauthorized!" },
				{ 1,"email5@mail.com", "username", "pass", "pass2", "0",
						new UserBean("username", "password", "email@email.com"), "ROLE_ADMIN",
						"Error: Password mismatch!" },
				{ 1,"email3@email.com", "username", "pass", "pass", "0",
						new UserBean("username", "password", "email@email.com"), "ROLE_USER", "Error: Email exists!" },
				{1,"email5@mail.com", "name2", "pass", "pass", "0",
						new UserBean("username", "password", "email@email.com"), "ROLE_USER",
						"Error: Username exists!" },
				{ 2,"email5@mail.com", "name", "pass", "pass", "0",
						new UserBean("username", "password", "email@email.com"), "ROLE_USER", "Error: Username exists!" }, });
	}
	@Parameter(0)
	public int id;
	@Parameter(1)
	public String email;
	@Parameter(2)
	public String username;
	@Parameter(3)
	public String password;
	@Parameter(4)
	public String repeatPassword;
	@Parameter(5)
	public String dietId;
	@Parameter(6)
	public UserBean loggedUser;
	@Parameter(7)
	public String roleCode;
	@Parameter(8)
	public String expectedResult;
	private UserController userController;
	private UserRepo userRepo;
	private UserPrincipal principal;
	//private RoleRepo roleRepo;
	//private DietRepo dietRepo= new DietRepo();
	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	@Rule
	public ErrorCollector collector = new ErrorCollector();

	@Before
	public void setup() {
		RoleBean role = new RoleBean();
		role.setCode(roleCode);
		role.setId(1);
		Set<RoleBean> roles = new HashSet<>(Arrays.asList(role));
		//roleRepo = mock(RoleRepo.class);
		userRepo = mock(UserRepo.class);
		if (loggedUser != null) {
			loggedUser.setRoles(roles);
			loggedUser.setId(1);
			principal = new UserPrincipal(loggedUser, roles);
		} else {
			principal = new UserPrincipal(null,roles);
		}
		UserBean user1 = new UserBean();
		user1.setId(1);
		user1.setUsername("username");
		user1.setPassword("password");
		user1.setEmail("email2@email.com");
		UserBean user2 = new UserBean();
		user2.setId(3);
		user2.setUsername("name2");
		user2.setPassword("pass2");
		user2.setEmail("email3@email.com");
		List<UserBean> users = new ArrayList<UserBean>();
		users.add(user1);
		users.add(user2);
		doReturn(users).when(userRepo).findAll();
		doReturn(user1).when(userRepo).getOne(id);
		//doReturn(role).when(roleRepo).findRoleByCode("ROLE_USER");
		userController = new UserController(userRepo, null, null, null, encoder,null);
	}

	@Test
	public void testUpdateUser() {
		final List<String> result = userController.updateUserAsAdmin(id,email, username, password, repeatPassword, dietId,
				principal);
		collector.checkThat(result.get(0), IsEqual.equalTo(expectedResult));

	}

}

