package com.project.nutricipe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.nutricipe.bean.ProductBean;
import com.project.nutricipe.bean.RecipeBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.repo.RecipeRepo;
import com.project.nutricipe.repo.UserRepo;
import com.project.nutricipe.security.UserPrincipal;
import com.project.nutricipe.services.DietService;
import com.project.nutricipe.services.ProductService;
import com.project.nutricipe.services.RecipeService;

@RestController
public class RecipeController {
@Autowired
private UserRepo userRepo;
@Autowired
private RecipeRepo recipeRepo;

public RecipeController(UserRepo userRepo, RecipeRepo recipeRepo) {
	this.userRepo = userRepo;
	this.recipeRepo = recipeRepo;
}
@GetMapping(path = "/recipe/all")
public List<RecipeBean> getAllRecipes(@AuthenticationPrincipal UserPrincipal principal) {
	UserBean user = principal.getLoggedInUser();
	if (user != null) {
		return recipeRepo.findAll();
	} else {
		return null;
	}
}

@GetMapping(path = "/recipe/{id}")
public RecipeBean getRecipeById(@PathVariable int id, @AuthenticationPrincipal UserPrincipal principal) {
	UserBean user = principal.getLoggedInUser();
	if (user != null) {
	Optional<RecipeBean> recipe = recipeRepo.findById(id);
	if (recipe.isPresent()) {
		return recipe.get();
	} else {
		return null;
	}
	} else {
		return null;
	}
}
@GetMapping(path = "/recipe/lastfive")
public List<RecipeBean> getLastFiveRecipes(@AuthenticationPrincipal UserPrincipal principal) {
	List<RecipeBean> result = new ArrayList<RecipeBean>();
	List<RecipeBean> recipes;
	UserBean user = principal.getLoggedInUser();
	if (user != null) {
		recipes = recipeRepo.findAll();
		if(recipes.size()<=5) {
			return recipes;
		}
		else {
			for (int i=0; i<5;i++) {
				result.add(recipes.get(recipes.size()-i+1));
			}
			return result;
		}
	} else {
		return null;
	}
}
@PostMapping(path="/recipe/product/diet")
public ResponseEntity<List<RecipeBean>> getRecipesByCategoriesAndProducts(@AuthenticationPrincipal UserPrincipal principal, @RequestParam(value = "productIds") List<String> productIds) {
	
	if(productIds!=null) {
		List<Integer> listProductIds = new ArrayList<Integer>();
		for(String id : productIds ) {
			System.out.println(id);
			listProductIds.add(Integer.parseInt(id));
		}
		UserBean user = principal.getLoggedInUser();
		ResponseEntity<List<RecipeBean>> response = RecipeService.getRecipesByCategoriesAndProducts(user, listProductIds);
		return response;
	}
	return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);


}
@PostMapping(path="/recipe/products")
public ResponseEntity<List<ProductBean>> getRecipeProducts(@AuthenticationPrincipal UserPrincipal principal, @RequestParam(value = "recipeId") String recipeId) {
	int id=0;
	if(recipeId!=null) {
		 id = (Integer.parseInt(recipeId));
		
		ResponseEntity<List<ProductBean>> response = RecipeService.getRecipeProducts(id);
		return response;
	}
	return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);


}
@PostMapping(path="/recipe/search")
public ResponseEntity<List<RecipeBean>> getRecipeBySearchString(@AuthenticationPrincipal UserPrincipal principal, @RequestParam(value = "searchTerm") String query) {
	//int id=0;
	if(query!=null) {
		 
		
		ResponseEntity<List<RecipeBean>> response = RecipeService.getRecipeBySearchString(query.trim());
		return response;
	}
	return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);


}
@PostMapping(path="/recipe")
public ResponseEntity<RecipeBean> createRecipe(@AuthenticationPrincipal UserPrincipal principal, @RequestParam(value="name") String name, @RequestParam(value="description") String description,
		/*@RequestParam(value="imagePath") String imagePath, @RequestParam(value="calories") String calories, @RequestParam(value="weight") String weight, @RequestParam(value="carbohydrates") String carbs,
		@RequestParam(value="proteins") String proteins, @RequestParam(value="fats") String fats,*/ @RequestParam(value="authorId") String authorId, @RequestParam(value="categoryIds") List<String> categoryIds,
		@RequestParam(value="productIds") List<String> ProductIds,@RequestParam(value = "image") MultipartFile file){
	
	UserBean user = principal.getLoggedInUser();
	if(user != null) {
	String imagePath = RecipeService.uploadRecipeImage(file);
	if (imagePath.equals("Image failed to upload!")|| imagePath.equals("No image was provided!")) {
		return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
	}
	else {
		ResponseEntity<RecipeBean> result = RecipeService.createRecipe(name, description, imagePath,authorId, ProductIds, categoryIds);
		return result;
	}
	}
	else {
		return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
	}
	
}
@PutMapping(path = "/recipe/{id}")
public ResponseEntity<RecipeBean> updateRecipe(@AuthenticationPrincipal UserPrincipal principal, @RequestParam(value="name") String name, @RequestParam(value="description") String description,
		/*@RequestParam(value="imagePath") String imagePath, @RequestParam(value="calories") String calories, @RequestParam(value="weight") String weight, @RequestParam(value="carbohydrates") String carbs,
		@RequestParam(value="proteins") String proteins, @RequestParam(value="fats") String fats,*/ @RequestParam(value="authorId") String authorId, @RequestParam(value="categoryIds") List<String> categoryIds,
		@RequestParam(value="productIds") List<String> ProductIds,@PathVariable String id,@RequestParam(value = "image",required=false) MultipartFile file){
	UserBean user = principal.getLoggedInUser();
	String imagePath="";
	if(user != null) {
		 imagePath = RecipeService.uploadRecipeImage(file);
		 if (imagePath.equals("Image failed to upload!")) {
				return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
			}
		 else {
			 ResponseEntity<RecipeBean> result = RecipeService.updateRecipe(id, name, description, imagePath,authorId, ProductIds, categoryIds);
				return result;
		 }
	}
	else {
		return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
	}
}
@DeleteMapping(path = "/recipe/{id}")
public ResponseEntity<Boolean> deleteRecipe(@AuthenticationPrincipal UserPrincipal principal,@PathVariable String id){
	UserBean user = principal.getLoggedInUser();
	if (user != null) {
		return RecipeService.deleteRecipe(id);
	} else {
		return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
	}

}
}


