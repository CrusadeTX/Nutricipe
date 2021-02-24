package com.project.nutricipe.controller.diet;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Arrays;
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

import com.project.nutricipe.bean.DietBean;
import com.project.nutricipe.controller.DietController;
import com.project.nutricipe.repo.DietRepo;

@RunWith(Parameterized.class)
public class GetDietByIdTest {
	@Parameters(name = "{index}: with id={0} and expected result={1}")
	public static Iterable<Object[]> data() {
		return Arrays.asList(new Object[][] { //
				 {0, null}, //1
				 {1, new DietBean(1,"Vegetarian",2000)}, //2
				 {2, null},//3
				 {3, null}//4
		});
	}
	@Parameter(0)
	public int id;
	@Parameter(1)
	public DietBean diet;
	private DietController dietController; 
	private DietRepo dietRepo;
	@Rule
	public ErrorCollector collector = new ErrorCollector();
	@Before
	public void setup() {
		dietRepo = mock(DietRepo.class);
		DietBean diet = new DietBean();
		diet.setId(1);
		diet.setName("Vegetarian");
		diet.setRecomendedCalories(2000);
		//DietBean diet2 = new DietBean();
		//diet.setId(2);
		//diet.setName("Carnivore");
		//diet.setRecomendedCalories(2500);	
		//List<DietBean> diets;
		//diets.add(diet);
		//diets.add(diet2);
		if(id==1) {
		doReturn(Optional.of(diet)).when(dietRepo).findById(id);
		}
		else {
		doReturn(Optional.empty()).when(dietRepo).findById(id);
		}
		dietController = new DietController(dietRepo, null);
	}
	@Test
	public void testGetDietById() {
		final DietBean result = dietController.getDietById(id);
		if(result!=null) {
		collector.checkThat(result.getId(),IsEqual.equalTo(id));
		collector.checkThat(result.getName(),IsEqual.equalTo(diet.getName()));
		collector.checkThat(result.getRecomendedCalories(),IsEqual.equalTo(diet.getRecomendedCalories()));
		//assertEquals(diet.getId(), result.getId());
		}
		else {
			collector.checkThat(result,IsEqual.equalTo(diet));
		}
	}
}
