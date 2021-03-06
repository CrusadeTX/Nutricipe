package com.project.nutricipe.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.nutricipe.bean.ProductBean;
import com.project.nutricipe.bean.RecipeBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.repo.RecipeRepo;
import com.project.nutricipe.repo.UserRepo;
import com.project.nutricipe.security.UserPrincipal;
import com.project.nutricipe.services.RecipeService;

@RestController
public class RecipeController {
@Autowired
private UserRepo userRepo;
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
}
