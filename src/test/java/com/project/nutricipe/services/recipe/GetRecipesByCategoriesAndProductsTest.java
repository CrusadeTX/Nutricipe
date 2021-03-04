package com.project.nutricipe.services.recipe;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
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

import com.project.nutricipe.bean.CategoryBean;
import com.project.nutricipe.bean.DietBean;
import com.project.nutricipe.bean.ProductBean;
import com.project.nutricipe.bean.RecipeBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.repo.DietRepo;
import com.project.nutricipe.repo.ProductRepo;
import com.project.nutricipe.repo.RecipeRepo;
import com.project.nutricipe.services.RecipeService;

@RunWith(Parameterized.class)
public class GetRecipesByCategoriesAndProductsTest {
	@Parameters(name = "{index}: with user = {0} , productIds {1} and expected result{2}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { //
				{ new UserBean("username","pass","email"),Arrays.asList(1), new ResponseEntity<>(null,HttpStatus.OK) },
		

		});
	}
	@Parameter(0)
	public UserBean user;
	@Parameter(1)
	public List<Integer> productIds;
	@Parameter(2)
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
		ProductBean product = new ProductBean();
		product.setId(1);
		product.setName("name");
		CategoryBean category = new CategoryBean();
		category.setId(1);
		category.setName("Name");
		category.setType("Adverse Effects");
		DietBean diet = new DietBean();
		diet.setId(1);
		diet.setCategories(new HashSet<>(Arrays.asList(category)));
		diet.setName("diet");
		user.setDiet(diet);
		user.setId(1);
		dietRepo=mock(DietRepo.class);
		recipeRepo=mock(RecipeRepo.class);
		doReturn(Optional.of(diet)).when(dietRepo).findById(user.getDiet().getId());
		RecipeBean recipe = new RecipeBean();
		recipe.setId(1);
		recipe.setCategories(new HashSet<>(Arrays.asList(category)));
		recipe.setName("recipe");
		recipe.setProducts(new HashSet<>(Arrays.asList(product)));
		doReturn(Arrays.asList(recipe)).when(recipeRepo).findAll();
		
		
		recipeService = new RecipeService(dietRepo,recipeRepo,null);
		
		
	}
	@Test
	public void testGetUserFridge() {
		final ResponseEntity<List<RecipeBean>> result = RecipeService.getRecipesByCategoriesAndProducts(user, productIds);
		
		collector.checkThat(result.getStatusCode(), IsEqual.equalTo(expectedResult.getStatusCode()));

	
	
	}
}
