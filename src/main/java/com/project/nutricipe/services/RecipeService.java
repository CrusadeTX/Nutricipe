package com.project.nutricipe.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.nutricipe.bean.CategoryBean;
import com.project.nutricipe.bean.DietBean;
import com.project.nutricipe.bean.FridgeBean;
import com.project.nutricipe.bean.ProductBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.repo.DietRepo;
import com.project.nutricipe.repo.ProductRepo;
import com.project.nutricipe.repo.RecipeRepo;
import com.project.nutricipe.bean.RecipeBean;

@Service
public class RecipeService {
	@Autowired
	private static DietRepo dietRepo;
	@Autowired
	private static RecipeRepo recipeRepo;
	@Autowired
	private static ProductRepo productRepo;
	
	public RecipeService(DietRepo dietRepo, RecipeRepo recipeRepo, ProductRepo productRepo) {
		this.dietRepo = dietRepo;
		this.recipeRepo = recipeRepo;
		this.productRepo = productRepo;
	}
	
	public  static ResponseEntity<List<RecipeBean>> getRecipesByCategoriesAndProducts(UserBean user, List<Integer> productIds) {
		List<RecipeBean> recipesWithRequestedCategories= new ArrayList<RecipeBean>();
		List<RecipeBean> result = new ArrayList<RecipeBean>();
		List<ProductBean> products = productRepo.findAll();
		int id = user.getDiet().getId();
		Optional<DietBean> diet = dietRepo.findById(id);
		if(diet.isPresent()) {
			Set<CategoryBean> dietCategories = diet.get().getCategories();
			List<RecipeBean> recipes = recipeRepo.findAll();
			if(dietCategories!=null) {
				for(CategoryBean dietCategory : dietCategories) {
					for(RecipeBean recipe : recipes) {
						Set<CategoryBean> recipeCategories = recipe.getCategories();
						for(CategoryBean recipeCategory: recipeCategories) {
							if(dietCategory.getName().equals(recipeCategory.getName())) {
								recipesWithRequestedCategories.add(recipe);
							}
						}
					}
				}
				for(RecipeBean recipe : recipesWithRequestedCategories) {
					int count =0;
					Set<ProductBean> recipeProducts = recipe.getProducts();
					for(ProductBean recipeProduct: recipeProducts) {
						for(ProductBean product: products) {
							if(recipeProduct.getId()==product.getId()) {
								count++;
							}
							if(recipeProducts.size()==count) {
								result.add(recipe);
							}
						}
						
					}
				}
				return new ResponseEntity(result,HttpStatus.OK);
			}
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
	}
}
