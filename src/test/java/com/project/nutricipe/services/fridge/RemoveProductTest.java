package com.project.nutricipe.services.fridge;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
import java.util.HashSet;
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

import com.project.nutricipe.bean.FridgeBean;
import com.project.nutricipe.bean.ProductBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.repo.ProductRepo;
import com.project.nutricipe.repo.UserRepo;
import com.project.nutricipe.services.FridgeService;

@RunWith(Parameterized.class)
public class RemoveProductTest {
	@Parameters(name = "{index}: with product_id = {0}, user_id= {1} and expected result={2}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { //
				{ 2, 1,false },
				{1,1,true}
				
				

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
		ProductBean product = new ProductBean();
		product.setId(1);
		product.setCalories(100);
		product.setName("Name");
		FridgeBean fridge = new FridgeBean();
		fridge.setId(1);
		fridge.setTotalCalories(2000);
		fridge.setProducts(new HashSet<>(Arrays.asList(product)));
		userRepo = mock(UserRepo.class);
		UserBean user = new UserBean();
		user.setId(user_id);
		user.setFridge(fridge);
		doReturn(user).when(userRepo).getOne(user_id);
		if(product_id==1) {
		doReturn(Optional.of(product)).when(productRepo).findById(product_id);
		}
		else {
		doReturn(Optional.empty()).when(productRepo).findById(product_id);
		}
		fridgeService= new FridgeService(null,productRepo,userRepo);
	}
	
	@Test
	public void testRemoveProduct() {
		final boolean result = fridgeService.removeProduct(product_id, user_id);
		
		collector.checkThat(result, IsEqual.equalTo(exptectedResult));
		
	
		
}
}
