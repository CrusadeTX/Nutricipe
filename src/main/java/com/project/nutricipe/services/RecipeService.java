package com.project.nutricipe.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.nutricipe.bean.CategoryBean;
import com.project.nutricipe.bean.DietBean;
import com.project.nutricipe.bean.FridgeBean;
import com.project.nutricipe.bean.ProductBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.repo.CategoryRepo;
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
	@Autowired
	private static CategoryRepo categoryRepo;
	
	public RecipeService(DietRepo dietRepo, RecipeRepo recipeRepo, ProductRepo productRepo, CategoryRepo categoryRepo) {
		this.dietRepo = dietRepo;
		this.recipeRepo = recipeRepo;
		this.productRepo = productRepo;
		this.categoryRepo = categoryRepo;
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
	public static ResponseEntity<RecipeBean> createRecipe(String name, String description, String imagePath, String authorId, List<String> productIds, List<String> categoryIds){
		double calories = 0;
		double weight =0;
		double proteins=0;
		double carbs=0;
		double fats =0;
		int authorIdInt = 0;
		Set<CategoryBean> categories = new HashSet<CategoryBean>();
		Set<ProductBean> products = new HashSet<ProductBean>();
		if(name !=null && description !=null && authorId!=null && productIds !=null && categoryIds !=null && imagePath!=null && Utilities.tryParseInt(authorId)) {
			String formattedName = name.toLowerCase().trim();
			List<RecipeBean> foundRecipes = recipeRepo.findAll();
			boolean recipeNameExists =false;
			if(foundRecipes != null) {
				for (RecipeBean recipe : foundRecipes) {
					if(recipe.getName().toLowerCase().equals(formattedName)) {
						recipeNameExists = true;
						break;
						
					}
				}
			}
			if(recipeNameExists) {
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
			}
			else {
			authorIdInt = Integer.parseInt(authorId);
			
			
			//Creating a List of Categories to add to the Recipe
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
			// Creating a List of Products to add to the Recipe, and calculating recipe nutrition data
			for (String id : productIds) {
				if(Utilities.tryParseInt(id)) {
					Optional<ProductBean> optionalProduct = productRepo.findById(Integer.parseInt(id));
					System.out.println("searching for a product");
					if(optionalProduct.isPresent()) {
						System.out.println("a product has been found");
						ProductBean foundProduct = optionalProduct.get();
						products.add(foundProduct);
						weight += foundProduct.getWeight();
						calories += foundProduct.getCalories();
						proteins += foundProduct.getProteins();
						carbs += foundProduct.getCarbohydrates();
						fats += foundProduct.getFats();
					}
					
				}
			}
			RecipeBean recipe = new RecipeBean();
			recipe.setName(name.trim());
			recipe.setDescription(description.trim());
			recipe.setImagePath(imagePath);
			recipe.setAuthorId(authorIdInt);
			recipe.setCalories(calories);
			recipe.setCarbohydrates(carbs);
			recipe.setProteins(proteins);
			recipe.setFats(fats);
			recipe.setWeight(weight);
			recipe.setCategories(categories);
			recipe.setProducts(products);
			RecipeBean result = recipeRepo.saveAndFlush(recipe);
			if(result !=null) {
				return new ResponseEntity<>(result, HttpStatus.CREATED);
			}
			else {
				return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
			}	
			}	
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
	}
	public static ResponseEntity<RecipeBean> updateRecipe(String recipeId, String name, String description, String imagePath, String authorId, List<String> productIds, List<String> categoryIds){
		double calories = 0;
		double weight =0;
		double proteins=0;
		double carbs=0;
		double fats =0;
		int authorIdInt = 0;
		Set<CategoryBean> categories = new HashSet<CategoryBean>();
		Set<ProductBean> products = new HashSet<ProductBean>();
		if(name !=null && description !=null && authorId!=null && productIds !=null && categoryIds !=null && imagePath!=null && Utilities.tryParseInt(authorId)&& Utilities.tryParseInt(recipeId)) {
			int recipeIdInt = Integer.parseInt(recipeId);
			String formattedName = name.toLowerCase().trim();
			Optional<RecipeBean> optionalOriginalRecipe = recipeRepo.findById(recipeIdInt);
			if (optionalOriginalRecipe.isPresent()) {
			RecipeBean originalRecipe = optionalOriginalRecipe.get();
			List<RecipeBean> foundRecipes = recipeRepo.findAll();
			boolean recipeNameExists =false;
			if(foundRecipes != null) {
				
				for (RecipeBean recipe : foundRecipes) {
					if(recipe.getName().toLowerCase().equals(formattedName) && !(recipe.getName().equals(originalRecipe.getName().toLowerCase()))) {
						
						recipeNameExists = true;
						break;
						
					}
				}
			}
			if(recipeNameExists) {
				return new ResponseEntity<>(null, HttpStatus.CONFLICT);
			}
			else {
			authorIdInt = Integer.parseInt(authorId);
			
			//RecipeBean recipe = new RecipeBean();
			//Creating a List of Categories to add to the Recipe
			if(categoryIds !=null) {
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
			originalRecipe.setCategories(categories);
			}
			// Creating a List of Products to add to the Recipe, and calculating recipe nutrition data
			if(productIds !=null) {
			for (String id : productIds) {
				if(Utilities.tryParseInt(id)) {
					Optional<ProductBean> optionalProduct = productRepo.findById(Integer.parseInt(id));
					System.out.println("searching for a product");
					if(optionalProduct.isPresent()) {
						System.out.println("a product has been found");
						ProductBean foundProduct = optionalProduct.get();
						products.add(foundProduct);
						weight += foundProduct.getWeight();
						calories += foundProduct.getCalories();
						proteins += foundProduct.getProteins();
						carbs += foundProduct.getCarbohydrates();
						fats += foundProduct.getFats();
					}
					
				}
			}
			originalRecipe.setProducts(products);
			originalRecipe.setCalories(calories);
			originalRecipe.setCarbohydrates(carbs);
			originalRecipe.setProteins(proteins);
			originalRecipe.setFats(fats);
			originalRecipe.setWeight(weight);
			}
			if(!imagePath.equals("No image was provided!")) {
				originalRecipe.setImagePath(imagePath);
			}
			
			originalRecipe.setName(name.trim());
			originalRecipe.setDescription(description.trim());
			originalRecipe.setAuthorId(authorIdInt);
			
			
			
			RecipeBean result = recipeRepo.saveAndFlush(originalRecipe);
			if(result !=null) {
				return new ResponseEntity<>(result, HttpStatus.CREATED);
			}
			else {
				return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
			}	
			}	
		}
			else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
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
	public static String uploadRecipeImage(MultipartFile file) {
		if(file!=null) {
			String userDirectory = Paths.get("")
			        .toAbsolutePath()
			        .toString();
			String uploadsPath = userDirectory + "\\src\\main\\resources\\public\\assets\\images\\";
			//Path path = Paths.get(uploadsPath);
			//Files.copy(file.getInputStream(), path);
			//Files.co
			//return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
			String fileExtension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
			String filename = new Random().nextInt(999999) + "_" + System.currentTimeMillis();
			File targetFile = new File(uploadsPath + filename + fileExtension);
			try {
			byte[] bytes = file.getBytes();
			file.transferTo(targetFile);
			}
			catch(IOException e) {
				e.printStackTrace();
				return "Image failed to upload!";
			}
			String uploadedDirectory = "/assets/images/"+filename+fileExtension;
			
			return uploadedDirectory;
		}
		else {
			return "No image was provided!";
		}
		//return null;
	}
	
	
}
