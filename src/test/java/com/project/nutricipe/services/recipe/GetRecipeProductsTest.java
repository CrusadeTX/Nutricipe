package com.project.nutricipe.services.recipe;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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

import com.project.nutricipe.bean.FridgeBean;
import com.project.nutricipe.bean.ProductBean;
import com.project.nutricipe.bean.RecipeBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.repo.DietRepo;
import com.project.nutricipe.repo.FridgeRepo;
import com.project.nutricipe.repo.ProductRepo;
import com.project.nutricipe.repo.RecipeRepo;
import com.project.nutricipe.services.FridgeService;
import com.project.nutricipe.services.RecipeService;

@RunWith(Parameterized.class)
public class GetRecipeProductsTest {
	@Parameters(name = "{index}: with recipeId = {0} and expected result={1}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { //
				{ 1, new ResponseEntity<>(null,HttpStatus.OK) },
		

		});
	}
	@Parameter(0)
	public int recipeId;
	@Parameter(1)
	public ResponseEntity<List<ProductBean>> expectedResult;
	private DietRepo dietRepo;
	private ProductRepo productRepo;
	private RecipeRepo recipeRepo;
	private RecipeService recipeService;
	//private UserRepo userRepo;
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	@Before
	public void setup() {
		//RoleBean role = new RoleBean();
		//role.setCode("ROLE_USER");
		//role.setId(1);
		//Set<RoleBean> roles = new HashSet<>(Arrays.asList(role));
		recipeRepo = mock(RecipeRepo.class);
		ProductBean product = new ProductBean();
		product.setId(1);
		product.setName("name");
		RecipeBean recipe = new RecipeBean();
		recipe.setProducts(new HashSet<>(new HashSet<>(Arrays.asList(product))));
		doReturn(Optional.of(recipe)).when(recipeRepo).findById(recipeId);
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
		recipeService = new RecipeService(null,recipeRepo,null,null);
		
		
	}
	@Test
	public void testGetUserFridge() {
		final ResponseEntity<List<ProductBean>> result = RecipeService.getRecipeProducts(recipeId);
		
		collector.checkThat(result.getStatusCode(), IsEqual.equalTo(expectedResult.getStatusCode()));

	
	
		
}

}
