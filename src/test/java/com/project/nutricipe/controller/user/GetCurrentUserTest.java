package com.project.nutricipe.controller.user;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

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

import com.project.nutricipe.bean.RoleBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.controller.UserController;
import com.project.nutricipe.repo.UserRepo;
import com.project.nutricipe.security.UserPrincipal;

@RunWith(Parameterized.class)
public class GetCurrentUserTest {
	@Parameters(name = "{index}: with loggedUser = {0} and expected result={1}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { //
				{ null,null},
				{ new UserBean("username","password","email@email.com"),new UserBean("username","password","email@email.com")},
		 });
	}
	@Parameter(0)
	public UserBean loggedUser;
	@Parameter(1)
	public UserBean expectedResult;
	private UserController userController;
	private UserRepo userRepo;
	private UserPrincipal principal;
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	@Before
	public void setup() {
		RoleBean role = new RoleBean();
		role.setCode("ROLE_USER");
		role.setId(1);
		Set<RoleBean> roles = new HashSet<>(Arrays.asList(role));
		userRepo = mock(UserRepo.class);
		if (loggedUser != null) {
			loggedUser.setRoles(roles);
			principal = new UserPrincipal(loggedUser, roles);
		} else {
			principal = new UserPrincipal(null,roles);
		}

		userController = new UserController(null, null, null, null, null);
	}
	@Test
	public void testGetCurrentUser() {
		
		final UserBean result = userController.getUser(principal);
		if (result != null) {
			collector.checkThat(result.getUsername(), IsEqual.equalTo(expectedResult.getUsername()));
			collector.checkThat(result.getPassword(), IsEqual.equalTo(expectedResult.getPassword()));
			collector.checkThat(result.getEmail(), IsEqual.equalTo(expectedResult.getEmail()));
		} else {
			collector.checkThat(result, IsEqual.equalTo(expectedResult));
		}

	}

}
