package com.project.nutricipe.services;

import java.util.ArrayList;
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
import com.project.nutricipe.bean.FridgeBean;
import com.project.nutricipe.bean.ProductBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.repo.DietRepo;
import com.project.nutricipe.repo.ProductRepo;
import com.project.nutricipe.repo.RecipeRepo;
import com.project.nutricipe.utilities.Utilities;
import com.project.nutricipe.bean.RecipeBean;
import java.util.Collections;

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
		
		Set<RecipeBean> recipesWithRequestedCategories= new HashSet<RecipeBean>();
		//Set<RecipeBean> recipesWithRequestedCategories= new HashSet<RecipeBean>();
		List<RecipeBean> result = new ArrayList<RecipeBean>();
		//List<ProductBean> products = productRepo.findAll();
		int id = user.getDiet().getId();
		Optional<DietBean> diet = dietRepo.findById(id);
		if(diet.isPresent()) {
			List<CategoryBean> dietCategories = new ArrayList<>(diet.get().getCategories());
			List<RecipeBean> recipes = recipeRepo.findAll();
			if(dietCategories!=null) {
				System.out.println("dietCategories isnt null");
					for(RecipeBean recipe : recipes) {
						boolean adverseEffectMatch= false;
						boolean added = false;
						List<CategoryBean> recipeCategories = new ArrayList<>(recipe.getCategories());
						//if(java.util.Collections.disjoint(recipeCategories,dietCategories)) {
							//recipesWithRequestedCategories.add(recipe);
						//} 
						for(CategoryBean recipeCategory: recipeCategories) {
							for(CategoryBean dietCategory : dietCategories) {
							if(dietCategory.getId()==recipeCategory.getId()) {
								
								boolean hasAdverseEffects = dietCategory.getType().equals("Adverse Effects");
								System.out.println(hasAdverseEffects);
								System.out.println(dietCategory.getName());
								if(hasAdverseEffects) {
									if(dietCategory.getId() == recipeCategory.getId()) {
										adverseEffectMatch=true;
										if(added) {
											recipesWithRequestedCategories.remove(recipe);
										}
										
										
									}
								}
								
								if(!adverseEffectMatch)	{
								recipesWithRequestedCategories.add(recipe);
								added=true;
								}
								
								
							}
						}
					}
				}
				for(RecipeBean recipe : recipesWithRequestedCategories) {
					System.out.println("================");
					System.out.println(recipe.getName());
					}
				System.out.println(recipesWithRequestedCategories.size());
				for(CategoryBean category :dietCategories ) {
					System.out.println("================");
					System.out.println(category.getName());
					}
				if(recipesWithRequestedCategories.size()>0) {
				for(RecipeBean recipe : recipesWithRequestedCategories) {
					int count =0;
					Set<ProductBean> recipeProducts = recipe.getProducts();
					for(ProductBean recipeProduct: recipeProducts) {
						for(int productId : productIds) {
							if(recipeProduct.getId()==productId) {
								count++;
							}
							
						}
						
					}
					System.out.println("**********");
					System.out.println(recipe.getName());
					System.out.println(recipeProducts.size());
					System.out.println(count);
					if(recipeProducts.size()==count) {
						result.add(recipe);
					}
				}
				
				return new ResponseEntity<>(result,HttpStatus.OK);
				}
				if(recipesWithRequestedCategories.size()==0) {
					for(RecipeBean recipe : recipes) {
						int count =0;
						Set<ProductBean> recipeProducts = recipe.getProducts();
						for(ProductBean recipeProduct: recipeProducts) {
							for(int productId : productIds) {
								if(recipeProduct.getId()==productId) {
									count++;
								}
								
							}
							
						}
						System.out.println("**********");
						System.out.println(recipe.getName());
						System.out.println(recipeProducts.size());
						System.out.println(count);
						if(recipeProducts.size()==count) {
							result.add(recipe);
						}
					}
				return new ResponseEntity<>(result,HttpStatus.OK);
				}
			}
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}		
	}
	public  static ResponseEntity<List<ProductBean>> getRecipeProducts(int recipeId){
		Optional<RecipeBean> recipe = recipeRepo.findById(recipeId);
		List<ProductBean> recipeProducts;
		if(recipe.isPresent()) {
			recipeProducts=new ArrayList<>(recipe.get().getProducts());
			if(recipeProducts!=null) {
				return new ResponseEntity<>(recipeProducts,HttpStatus.OK);
			}
			else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}
		else {
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	public  static ResponseEntity<List<RecipeBean>> getRecipeBySearchString(String query){
		List<RecipeBean> allRecipes = recipeRepo.findAll();
		for (RecipeBean recipe : allRecipes) {
			//System.out.println("Recipe:");
		
			//System.out.println(recipe.getName());
		}
	
		List<RecipeBean> result = new ArrayList<RecipeBean>();
		for(RecipeBean recipe : allRecipes) {
			if(recipe.getName().toLowerCase().contains(query.toLowerCase())) {
				result.add(recipe);
				System.out.println("Recult Recipe:");
				
				System.out.println(recipe.getName());
				
			}
		}
		if(result.size()>0) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		
	}
	public static ResponseEntity<RecipeBean> createRecipe(String name, String description, String imagePath, String weight, List<ProductBean> products, List<CategoryBean> categories){
		return null;
	}
	public static ResponseEntity<RecipeBean> updateRecipe(String id, String name, String description, String imagePath, String weight, List<ProductBean> products, List<CategoryBean> categories){
		return null;
	}
	public static ResponseEntity<Boolean> deleteRecipe(String id){
		if(Utilities.tryParseInt(id)) {
			Optional<RecipeBean> optionalRecipe = recipeRepo.findById(Integer.parseInt(id));
			if (optionalRecipe.isPresent()) {
				RecipeBean recipe = optionalRecipe.get();
				recipeRepo.delete(recipe);
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
	
	
}
