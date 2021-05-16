package com.project.nutricipe.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.nutricipe.bean.CategoryBean;
import com.project.nutricipe.bean.ProductBean;
import com.project.nutricipe.bean.RecipeBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.repo.DietRepo;
import com.project.nutricipe.repo.ProductRepo;
import com.project.nutricipe.repo.RecipeRepo;
@Service
public class ProductService {
	@Autowired
	private static DietRepo dietRepo;
	@Autowired
	private static RecipeRepo recipeRepo;
	@Autowired
	private static ProductRepo productRepo;
	
	public ProductService(DietRepo dietRepo, RecipeRepo recipeRepo, ProductRepo productRepo) {
		this.dietRepo = dietRepo;
		this.recipeRepo = recipeRepo;
		this.productRepo = productRepo;
	}
	public  static ResponseEntity<List<ProductBean>> getProductBySearchString(String query){
		List<ProductBean> allProducts = productRepo.findAll();
		List<ProductBean> result = new ArrayList<ProductBean>();
		for(ProductBean product : allProducts) {
			if(product.getName().toLowerCase().contains(query.toLowerCase())) {
				result.add(product);
			}
		}
		if(result.size()>0) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		
	}
	public static ResponseEntity<ProductBean> createProduct (String name, String description, String imagePath, String caloriesString, String weightString, String carbsString, String proteinsString, String fatsString, String authorId){
		double calories = 0;
		double weight =0;
		double proteins=0;
		double carbs=0;
		double fats =0;
		int authorIdInt = 0;
		if(tryParseDouble(caloriesString) && tryParseDouble(weightString) && tryParseDouble(carbsString) && tryParseDouble(proteinsString) && tryParseDouble(fatsString) && tryParseInt(authorId)) {
			List<ProductBean> foundProducts = productRepo.findAll();
			String formattedName = name.trim().toLowerCase();
			boolean productNameExists = false;
			if(foundProducts!=null) {
				for(ProductBean productBean : foundProducts) {
					if(productBean.getName().toLowerCase().equals(formattedName)) {
						productNameExists = true;
					}
				}
			}
			if(productNameExists) {
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
			}
			else
			{
			calories = Double.parseDouble(caloriesString);
			weight = Double.parseDouble(weightString);
			proteins = Double.parseDouble(proteinsString);
			carbs = Double.parseDouble(carbsString);
			fats = Double.parseDouble(fatsString);
			authorIdInt = Integer.parseInt(authorId);
			ProductBean product = new ProductBean();
			product.setCalories(calories);
			product.setCarbohydrates(carbs);
			product.setProteins(proteins);
			product.setFats(fats);
			product.setWeight(weight);
			product.setName(name);
			product.setImagePath(imagePath);
			product.setDescription(description);
			product.setAuthorId(authorIdInt);
			ProductBean result = productRepo.saveAndFlush(product);
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
	private static boolean tryParseDouble(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
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
	public static ResponseEntity<Boolean> deleteProduct(int id) {
		Optional<ProductBean> optionalProduct = productRepo.findById(id);
		if (optionalProduct.isPresent()) {
			ProductBean product = optionalProduct.get();
			productRepo.delete(product);
			return new ResponseEntity<>(true, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
		}
	}
	public static ResponseEntity<ProductBean> updateProduct (String id, String name, String description, String imagePath, String caloriesString, String weightString, String carbsString, String proteinsString, String fatsString){
		double calories = 0;
		double weight =0;
		double proteins=0;
		double carbs=0;
		double fats =0;
		int productId=0;
		
		if(tryParseInt(id) && tryParseDouble(caloriesString) && tryParseDouble(weightString) && tryParseDouble(carbsString) && tryParseDouble(proteinsString) && tryParseDouble(fatsString)) {
			productId = Integer.parseInt(id);
			List<ProductBean> foundProducts = productRepo.findAll();
			String formattedName = name.trim().toLowerCase();
			Optional<ProductBean> optionalProduct = productRepo.findById(productId);
			ProductBean product = new ProductBean();
			if(optionalProduct.isPresent()) {
				 product = optionalProduct.get();
			}
			boolean productNameExists = false;
			if(foundProducts!=null) {
				for(ProductBean productBean : foundProducts) {
					if(productBean.getName().toLowerCase().equals(formattedName)&&!(product.getName().toLowerCase().equals(productBean.getName().toLowerCase()))) {
						productNameExists = true;
					}
				}
			}
			if(productNameExists) {
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
			}
			else
			{
			
			calories = Double.parseDouble(caloriesString);
			weight = Double.parseDouble(weightString);
			proteins = Double.parseDouble(proteinsString);
			carbs = Double.parseDouble(carbsString);
			fats = Double.parseDouble(fatsString);
			
			
			if(optionalProduct.isPresent()) {
			//product = optionalProduct.get();
			product.setCalories(calories);
			product.setCarbohydrates(carbs);
			product.setProteins(proteins);
			product.setFats(fats);
			product.setWeight(weight);
			product.setName(name);
			product.setImagePath(imagePath);
			product.setDescription(description);
			ProductBean result = productRepo.saveAndFlush(product);
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
	public static ResponseEntity<List<ProductBean>> getAllProducts() {
		
		List<ProductBean> result = productRepo.findAll();
		if(result!=null) {
		
		
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
	

}
