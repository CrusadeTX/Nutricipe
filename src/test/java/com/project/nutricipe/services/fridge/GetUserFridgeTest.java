package com.project.nutricipe.services.fridge;

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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import com.project.nutricipe.bean.FridgeBean;
import com.project.nutricipe.bean.RoleBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.controller.UserController;
import com.project.nutricipe.repo.FridgeRepo;
import com.project.nutricipe.repo.ProductRepo;
import com.project.nutricipe.repo.UserRepo;
import com.project.nutricipe.security.UserPrincipal;
import com.project.nutricipe.services.FridgeService;

@RunWith(Parameterized.class)
public class GetUserFridgeTest {
	@Parameters(name = "{index}: with logged on user = {0} and expected result={1}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { //
				{ null, null },
				{ new UserBean("username", "password", "email@email.com"),
						new FridgeBean(1,2000) }

		});
	}
	@Parameter(0)
	public UserBean loggedUser;
	@Parameter(1)
	public FridgeBean expectedResult;
	private FridgeRepo fridgeRepo;
	private ProductRepo productRepo;
	private FridgeService fridgeService;
	//private UserRepo userRepo;
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	@Before
	public void setup() {
		//RoleBean role = new RoleBean();
		//role.setCode("ROLE_USER");
		//role.setId(1);
		//Set<RoleBean> roles = new HashSet<>(Arrays.asList(role));
		fridgeRepo = mock(FridgeRepo.class);
		FridgeBean fridge = new FridgeBean();
		fridge.setId(1);
		fridge.setTotalCalories(2000);
		//principal = new UserPrincipal(loggedUser, roles);
		//UserBean user1 = new UserBean();
		// user1.setId(1);
		//user1.setUsername("name");
		//user1.setPassword("pass");
		//user1.setEmail("email2@email.com");
		//UserBean user2 = new UserBean();
		// user2.setId(3);
		//user2.setUsername("name2");
		//user2.setPassword("pass2");
		//user2.setEmail("email3@email.com");
		//List<UserBean> users = new ArrayList<UserBean>();
		//users.add(user1);
		//users.add(user2);
		
		
		if(loggedUser!=null) {
		doReturn(Optional.of(fridge)).when(fridgeRepo).findById(1);
		loggedUser.setFridge(fridge);
		}
		else {
		doReturn(Optional.of(fridge)).when(fridgeRepo).findById(5);
		}
		fridgeService= new FridgeService(fridgeRepo,null,null);
	}
	@Test
	public void testGetUserFridge() {
		final FridgeBean result = fridgeService.getUserFridge(loggedUser);
		if(loggedUser!=null) {
		collector.checkThat(result.getId(), IsEqual.equalTo(expectedResult.getId()));
		collector.checkThat(result.getTotalCalories(), IsEqual.equalTo(expectedResult.getTotalCalories()));
		}
		else {
		collector.checkThat(result, IsEqual.equalTo(expectedResult));
		}
		
}
}
