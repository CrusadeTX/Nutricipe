package com.project.nutricipe.controller.diet;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import com.project.nutricipe.bean.DietBean;
import com.project.nutricipe.controller.DietController;
import com.project.nutricipe.repo.DietRepo;

public class GetAllDietsTest {
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
		DietBean diet2 = new DietBean();
		diet2.setId(2);
		diet2.setName("Carnivore");
		diet2.setRecomendedCalories(2500);	
		List<DietBean> diets = new ArrayList<DietBean>();
		diets.add(diet);
		diets.add(diet2);
		doReturn(diets).when(dietRepo).findAll();
		dietController = new DietController(dietRepo, null);
	}
	@Test
	public void testGetDietById() {
		final List<DietBean> result = dietController.getAllDiets();
		collector.checkThat(result.size(),IsEqual.equalTo(2));
		collector.checkThat(result.get(0).getName(),IsEqual.equalTo("Vegetarian"));
		collector.checkThat(result.get(0).getId(),IsEqual.equalTo(1));
		collector.checkThat(result.get(0).getRecomendedCalories(),IsEqual.equalTo(2000.0));
		collector.checkThat(result.get(1).getName(),IsEqual.equalTo("Carnivore"));
		collector.checkThat(result.get(1).getId(),IsEqual.equalTo(2));
		collector.checkThat(result.get(1).getRecomendedCalories(),IsEqual.equalTo(2500.0));
		

	
	}

}
