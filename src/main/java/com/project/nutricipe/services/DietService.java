package com.project.nutricipe.services;

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
	
	
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	
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
			List<DietBean> foundDiets = dietRepo.findAll();
			String formattedName = name.trim().toLowerCase();
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
			Optional<DietBean> optionalDiet = dietRepo.findById(dietId);
			
			if(optionalDiet.isPresent()) {
				
			DietBean diet = optionalDiet.get();
			
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

}
