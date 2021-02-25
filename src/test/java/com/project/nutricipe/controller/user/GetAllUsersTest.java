package com.project.nutricipe.controller.user;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.project.nutricipe.bean.RoleBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.controller.UserController;
import com.project.nutricipe.repo.UserRepo;
import com.project.nutricipe.security.UserPrincipal;

@RunWith(Parameterized.class)
public class GetAllUsersTest {
	@Parameters(name = "{index}: with logged on user = {0} and expected result={1}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { //
				{ null, null },
				{ new UserBean("username", "password", "email@email.com"),
						Arrays.asList(new UserBean("name", "pass", "email2@email.com"),
								new UserBean("name2", "pass2", "email3@email.com")) }

		});
	}

	@Parameter(0)
	public UserBean loggedUser;
	@Parameter(1)
	public List<UserBean> expectedResult;
	private UserController userController;
	private UserRepo userRepo;
	private UserPrincipal principal;
	private PasswordEncoder encoder = new BCryptPasswordEncoder();
	@Rule
	public ErrorCollector collector = new ErrorCollector();

	@Before
	public void setup() {
		RoleBean role = new RoleBean();
		role.setCode("ROLE_USER");
		role.setId(1);
		Set<RoleBean> roles = new HashSet<>(Arrays.asList(role));
		userRepo = mock(UserRepo.class);
		principal = new UserPrincipal(loggedUser, roles);
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
		userController = new UserController(userRepo, null, null, null, encoder);
	}

	@Test
	public void testGetAllUsers() {
		int matches = 0;
		final List<UserBean> result = userController.getAllUsers(principal);
		if (result != null) {
			Arrays.asList(result).equals(expectedResult);
			for (UserBean user : result) {
				for (UserBean expectedUser : expectedResult) {
					if (user.getUsername().equals(expectedUser.getUsername())
							&& user.getPassword().equals(expectedUser.getPassword())
							&& user.getEmail().equals(expectedUser.getEmail())) {
						matches++;
					}
				}
			}
			collector.checkThat(matches, IsEqual.equalTo(2));
		} else {
			collector.checkThat(result, IsEqual.equalTo(expectedResult));
		}

	}

}
