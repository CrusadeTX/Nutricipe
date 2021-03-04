package com.project.nutricipe.services.fridge;

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
	import org.springframework.beans.factory.annotation.Autowired;

	
	import com.project.nutricipe.bean.FridgeBean;
import com.project.nutricipe.bean.ProductBean;
import com.project.nutricipe.bean.RoleBean;
	import com.project.nutricipe.bean.UserBean;
	import com.project.nutricipe.controller.UserController;
	import com.project.nutricipe.repo.FridgeRepo;
	import com.project.nutricipe.repo.ProductRepo;
	import com.project.nutricipe.repo.UserRepo;
	import com.project.nutricipe.security.UserPrincipal;
	import com.project.nutricipe.services.FridgeService;

	@RunWith(Parameterized.class)
	public class AddProductTest { 
		@Parameters(name = "{index}: with product_id = {0}, user_id= {1} and expected result={2}")
		public static Iterable<Object[]> data() {
			return Arrays.asList(new Object[][] { //
					{ 1, 1,true },
					
					

			});
		}
		@Parameter(0)
		public int product_id;
		@Parameter(1)
		public int user_id;
		@Parameter(2)
		public boolean exptectedResult;
		//private FridgeRepo fridgeRepo;
		private ProductRepo productRepo;
		private FridgeService fridgeService;
		private UserRepo userRepo;
		@Rule
		public ErrorCollector collector = new ErrorCollector();
		@Before
		public void setup() {
			productRepo = mock(ProductRepo.class);
			FridgeBean fridge = new FridgeBean();
			fridge.setId(1);
			fridge.setTotalCalories(2000);
			fridge.setProducts(new HashSet<ProductBean>());
			userRepo = mock(UserRepo.class);
			UserBean user = new UserBean();
			user.setId(user_id);
			user.setFridge(fridge);
			doReturn(user).when(userRepo).getOne(user_id);
			productRepo = mock(ProductRepo.class);
			ProductBean product = new ProductBean();
			product.setId(1);
			product.setCalories(100);
			product.setName("Name");
			doReturn(Optional.of(product)).when(productRepo).findById(product_id);
			fridgeService= new FridgeService(null,productRepo,userRepo);
		}
		@Test
		public void testAddProduct() {
			final boolean result = fridgeService.addProduct(product_id, user_id);
			
			collector.checkThat(result, IsEqual.equalTo(exptectedResult));
			
		
			
	}
		
	

}
