package com.project.nutricipe.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.nutricipe.bean.CategoryBean;
import com.project.nutricipe.bean.DietBean;
import com.project.nutricipe.repo.CategoryRepo;
import com.project.nutricipe.repo.DietRepo;
import com.project.nutricipe.utilities.Utilities;
@Service
public class DietService {
@Autowired
private static DietRepo dietRepo;
@Autowired
private static CategoryRepo categoryRepo;
public DietService(DietRepo dietRepo, CategoryRepo categoryRepo) {
	this.dietRepo = dietRepo;
	this.categoryRepo = categoryRepo;
}
public  static ResponseEntity<List<DietBean>> getAllDiets(){
	List<DietBean> result = dietRepo.findAll();
	if(result!=null) {
		if(!result.isEmpty()) {
	
		System.out.println("result is null===================");
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	else {
		DietBean diet = new DietBean();
		diet.setName("No Diet");
		diet.setRecomendedCalories(2000);
		DietBean resultDiet = dietRepo.saveAndFlush(diet);
		if(resultDiet!=null) {
		return new ResponseEntity<>(Arrays.asList(resultDiet),HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
		
	}
	else {
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	
	
}
public static ResponseEntity<DietBean> getDietById(String id) {
	if(Utilities.tryParseInt(id)) {
	Optional<DietBean> optionalDiet = dietRepo.findById(Integer.parseInt(id));
	if (optionalDiet.isPresent()) {
		DietBean diet = optionalDiet.get();
		
		return new ResponseEntity<>(diet, HttpStatus.OK);
	}
	else {
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	}
	else {
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
}






	public static ResponseEntity<Boolean> deleteDiet(String id) {
		if(Utilities.tryParseInt(id)) {
			Optional<DietBean> optionalDiet = dietRepo.findById(Integer.parseInt(id));
			if (optionalDiet.isPresent()) {
				DietBean diet = optionalDiet.get();
				dietRepo.delete(diet);
				return new ResponseEntity<>(true, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
			}
			}
			else {
				return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
			}
	}

	public static ResponseEntity<DietBean> updateDiet(String id, String name, String recCalories, List<String> categoryIds) {
		if(Utilities.tryParseInt(id) && name!=null && recCalories !=null && Utilities.tryParseDouble(recCalories.trim())) {
			int dietId = Integer.parseInt(id);
			double parsedRecCalories = Double.parseDouble(recCalories);
			Set<CategoryBean> categories = new HashSet<CategoryBean>();
			Optional<DietBean> optionalDiet = dietRepo.findById(dietId);
			DietBean diet = new DietBean();
			if(optionalDiet.isPresent()) {
			diet = optionalDiet.get();
			}
			
			List<DietBean> foundDiets = dietRepo.findAll();
			String formattedName = name.trim().toLowerCase();
			boolean dietNameExists = false;
			if(foundDiets!=null) {
				for(DietBean dietBean : foundDiets) {
					if(dietBean.getName().toLowerCase().equals(formattedName) &&!(dietBean.getName().equals(diet.getName().toLowerCase()))) {
						dietNameExists = true;
						break;
					}
				}
			}
			if(dietNameExists) {
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
			}
			else
			{
			
			
			if(optionalDiet.isPresent()) {
				
			//DietBean diet = optionalDiet.get();
			
			diet.setName(formattedName);
			diet.setRecomendedCalories(parsedRecCalories);
			if(categoryIds !=null) {
				for (String categoryId : categoryIds) {
					if(Utilities.tryParseInt(id)) {
						Optional<CategoryBean> optionalCategory = categoryRepo.findById(Integer.parseInt(categoryId));
						if(optionalCategory.isPresent()) {
							CategoryBean foundCategory = optionalCategory.get();
							categories.add(foundCategory);
						}
						
					}
				}
				diet.setCategories(categories);
				
			}
			DietBean result = dietRepo.saveAndFlush(diet);
			if(result != null) {
				return new ResponseEntity<>(result, HttpStatus.OK);
				
			}
			else {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
			else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}
	}
		else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	public static ResponseEntity<DietBean> createDiet(String name, String recCalories,List<String> categoryIds) {
		if(name!=null && Utilities.tryParseDouble(recCalories.trim())) {
			List<DietBean> foundDiets = dietRepo.findAll();
			String formattedName = name.trim().toLowerCase();
			double parsedRecCalories = Double.parseDouble(recCalories);
			Set<CategoryBean> categories = new HashSet<CategoryBean>();
			boolean dietNameExists = false;
			if(foundDiets!=null) {
				for(DietBean diet : foundDiets) {
					if(diet.getName().toLowerCase().equals(formattedName)) {
						dietNameExists = true;
					}
				}
			}
			if(dietNameExists) {
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
			}
			else
			{	
				if(categoryIds !=null) {
					System.out.println("category ids isnt null");
				for (String id : categoryIds) {
					if(Utilities.tryParseInt(id)) {
						Optional<CategoryBean> optionalCategory = categoryRepo.findById(Integer.parseInt(id));
						System.out.println("searching for a category");
						if(optionalCategory.isPresent()) {
							System.out.println("a category has been found");
							CategoryBean foundCategory = optionalCategory.get();
							categories.add(foundCategory);
						}
						
					}
				}
				
			}
				DietBean diet = new DietBean();
				diet.setName(formattedName);
				diet.setRecomendedCalories(parsedRecCalories);
				
				diet.setCategories(categories);
				for(CategoryBean category : diet.getCategories()) {
					System.out.println("Category:" + category.getName());
				}
				DietBean result = dietRepo.saveAndFlush(diet);
			if(result != null) {
				return new ResponseEntity<>(result, HttpStatus.CREATED);
				
			}
			else {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			}
			}
			else {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		
	}
	public static ResponseEntity<List<DietBean>> getDietBySearchString(String query) {
		List<DietBean> allDiets = dietRepo.findAll();
		for (DietBean diet : allDiets) {
			//System.out.println("Recipe:");
		
			//System.out.println(recipe.getName());
		}

		List<DietBean> result = new ArrayList<DietBean>();
		for(DietBean diet : allDiets) {
			if(diet.getName().toLowerCase().contains(query.toLowerCase())) {
				result.add(diet);
				System.out.println("Result Diet:");
				
				System.out.println(diet.getName());
				
			}
		}
		if(result.size()>0) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

}
