package com.project.nutricipe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.project.nutricipe.repo.CategoryRepo;
import com.project.nutricipe.repo.UserRepo;

@RestController
public class CategoryController {
	@Autowired
	private CategoryRepo categoryRepo;
	private UserRepo userRepo;
public CategoryController(CategoryRepo categoryRepo, UserRepo userRepo) {
	this.categoryRepo = categoryRepo;
	this.userRepo = userRepo;
}

}
