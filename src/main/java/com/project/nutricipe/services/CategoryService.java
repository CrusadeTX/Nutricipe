package com.project.nutricipe.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.nutricipe.bean.CategoryBean;
import com.project.nutricipe.repo.CategoryRepo;

@Service
public class CategoryService {
@Autowired
private static CategoryRepo categoryRepo;
public CategoryService(CategoryRepo categoryRepo) {
	this.categoryRepo = categoryRepo;
}

public  static ResponseEntity<List<CategoryBean>> getAllCategories(){
	List<CategoryBean> result = categoryRepo.findAll();
	if(result!=null) {
	
	
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	
}
public static ResponseEntity<CategoryBean> getCategoryById(String id) {
	if(tryParseInt(id)) {
	Optional<CategoryBean> optionalCategory = categoryRepo.findById(Integer.parseInt(id));
	if (optionalCategory.isPresent()) {
		CategoryBean category = optionalCategory.get();
		
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	else {
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	}
	else {
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
}
public static ResponseEntity<CategoryBean> createCategory (String name, String type){
		if(name!=null && type!=null) {
		List<CategoryBean> foundCategories = categoryRepo.findAll();
		String formattedName = name.trim().toLowerCase();
		String formattedType = type.trim();
		if(!checkIfCategoryTypeIsValid(formattedType)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		boolean categoryNameExists = false;
		if(foundCategories!=null) {
			for(CategoryBean category : foundCategories) {
				if(category.getName().toLowerCase().equals(formattedName)&& category.getType().toLowerCase().trim().equals(formattedType.toLowerCase())) {
					categoryNameExists = true;
				}
			}
		}
		if(categoryNameExists) {
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		}
		else
		{
		CategoryBean category = new CategoryBean();
		category.setName(name.trim());
		category.setType(type.trim());
		CategoryBean result = categoryRepo.saveAndFlush(category);
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
public static ResponseEntity<Boolean> deleteCategory(String id) {
	if(tryParseInt(id)) {
	Optional<CategoryBean> optionalCategory = categoryRepo.findById(Integer.parseInt(id));
	if (optionalCategory.isPresent()) {
		CategoryBean category = optionalCategory.get();
		categoryRepo.delete(category);
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
public static ResponseEntity<CategoryBean> updateCategory (String id, String name, String type){
	if(tryParseInt(id) && name!=null && type !=null) {
		int categoryId = Integer.parseInt(id);
		List<CategoryBean> foundCategories = categoryRepo.findAll();
		String formattedName = name.trim().toLowerCase();
		String formattedType = type.trim();
		if(!checkIfCategoryTypeIsValid(formattedType)) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		Optional<CategoryBean> optionalCategory = categoryRepo.findById(categoryId);
		CategoryBean category = new CategoryBean();
		if(optionalCategory.isPresent()) {
		 category = optionalCategory.get();
		}
		boolean categoryNameExists = false;
		if(foundCategories!=null) {
			for(CategoryBean categoryBean : foundCategories) {
				if(categoryBean.getName().toLowerCase().equals(formattedName)&& categoryBean.getType().toLowerCase().trim().equals(formattedType.toLowerCase())&&!(category.getName().toLowerCase().equals(categoryBean.getName().toLowerCase()))) {
					categoryNameExists = true;
				}
			}
		}
		if(categoryNameExists) {
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		}
		else
		{
		//Optional<CategoryBean> optionalCategory = categoryRepo.findById(categoryId);
		
		if(optionalCategory.isPresent()) {
		//CategoryBean category = optionalCategory.get();
		category.setName(name.trim());
		category.setType(type.trim());
		CategoryBean result = categoryRepo.saveAndFlush(category);
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
private static boolean tryParseInt(String value) {
	try {
		Integer.parseInt(value);
		return true;
	} catch (NumberFormatException e) {
		return false;
	}
}
private static boolean checkIfCategoryTypeIsValid(String categoryType) {
	switch(categoryType) {
	case "Adverse Effects": return true;
	case "Dish Types": return true;
	case "Benefit": return true;
	case "Food Types": return true;
	default: return false;
	}
}


}

