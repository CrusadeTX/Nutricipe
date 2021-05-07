package com.project.nutricipe.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.nutricipe.bean.CategoryBean;
import com.project.nutricipe.bean.DietBean;
import com.project.nutricipe.bean.ProductBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.repo.CategoryRepo;
import com.project.nutricipe.repo.UserRepo;
import com.project.nutricipe.security.UserPrincipal;
import com.project.nutricipe.services.CategoryService;

@RestController
public class CategoryController {
	@Autowired
	private CategoryRepo categoryRepo;
	private UserRepo userRepo;
public CategoryController(CategoryRepo categoryRepo, UserRepo userRepo) {
	this.categoryRepo = categoryRepo;
	this.userRepo = userRepo;
}

@GetMapping(path = "/category")
public ResponseEntity<List<CategoryBean>> getAllCategories(@AuthenticationPrincipal UserPrincipal principal) {
	UserBean user = principal.getLoggedInUser();
	if (user != null) {
		return CategoryService.getAllCategories();
	} else {
		return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
	}
}


@GetMapping(path = "/category/{id}")
public ResponseEntity<CategoryBean> getCategoryById(@AuthenticationPrincipal UserPrincipal principal, @PathVariable String id) {
	UserBean user = principal.getLoggedInUser();
	if (user != null) {
		return CategoryService.getCategoryById(id);
	} else {
		return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
	}


}
@PostMapping(path = "/category")
public ResponseEntity<CategoryBean> createCategory(@AuthenticationPrincipal UserPrincipal principal, @RequestParam (value="name")String name,@RequestParam(value="type") String type ) {
	UserBean user = principal.getLoggedInUser();
	if (user != null) {
		return CategoryService.createCategory(name, type);
	} else {
		return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
	}

}
@PutMapping(path = "/category/{id}")
public ResponseEntity<CategoryBean> updateCategory(@AuthenticationPrincipal UserPrincipal principal, @RequestParam (value="name")String name,@RequestParam(value="type") String type,@PathVariable String id) {
	UserBean user = principal.getLoggedInUser();
	if (user != null) {
		return CategoryService.updateCategory(id,name, type);
	} else {
		return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
	}

}
@DeleteMapping(path = "/category/{id}")
public ResponseEntity<Boolean> deleteCategory(@AuthenticationPrincipal UserPrincipal principal,@PathVariable String id){
	UserBean user = principal.getLoggedInUser();
	if (user != null) {
		return CategoryService.deleteCategory(id);
	} else {
		return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
	}

}
}
