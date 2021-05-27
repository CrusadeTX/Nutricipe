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
import com.project.nutricipe.repo.DietRepo;
import com.project.nutricipe.repo.UserRepo;
import com.project.nutricipe.security.UserPrincipal;
import com.project.nutricipe.services.CategoryService;
import com.project.nutricipe.services.DietService;

@RestController
public class DietController {
	@Autowired
	private DietRepo dietRepo;
	private UserRepo userRepo;

	public DietController(DietRepo dietRepo, UserRepo userRepo) {
		this.dietRepo = dietRepo;
		this.userRepo = userRepo;
	}

	@GetMapping(path = "/diet/all")
	public List<DietBean> getAllDiets() {
		return dietRepo.findAll();
	}
	@GetMapping(path = "/diet/initial")
	public ResponseEntity<List<DietBean>> getAllDietsInitially() {
		return DietService.getAllDiets();
	}

	@GetMapping(path = "/diet/{id}")
	public DietBean getDietById(@PathVariable int id) {
		Optional<DietBean> diet = dietRepo.findById(id);
		if (diet.isPresent()) {
			return diet.get();
		} else {
			return null;
		}


	}
	@GetMapping(path = "/diet")
	public ResponseEntity<List<DietBean>> retrieveAllDiets(@AuthenticationPrincipal UserPrincipal principal) {
		UserBean user = principal.getLoggedInUser();
		if (user != null) {
			return DietService.getAllDiets();
		} else {
			return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
		}

	}
	@PostMapping(path = "/diet")
	public ResponseEntity<DietBean> createDiet(@AuthenticationPrincipal UserPrincipal principal, @RequestParam (value="name")String name,@RequestParam(value="recCalories") String recCalories,@RequestParam (value="categoryIds")List<String> categoryIds) {
		UserBean user = principal.getLoggedInUser();
		if (user != null) {
			for(String id : categoryIds) {
				System.out.println(id);
			}
			return DietService.createDiet(name, recCalories, categoryIds);
		} else {
			return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
		}

	}
	@PutMapping(path = "/diet/{id}")
	public ResponseEntity<DietBean> updateDiet(@AuthenticationPrincipal UserPrincipal principal, @RequestParam (value="name")String name,@RequestParam(value="recCalories") String recCalories, @RequestParam (value="categoryIds")List<String> categoryIds,@PathVariable String id) {
		UserBean user = principal.getLoggedInUser();
		if (user != null) {
			return DietService.updateDiet(id,name, recCalories, categoryIds);
		} else {
			return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
		}

	}
	@DeleteMapping(path = "/diet/{id}")
	public ResponseEntity<Boolean> deleteDiet(@AuthenticationPrincipal UserPrincipal principal,@PathVariable String id){
		UserBean user = principal.getLoggedInUser();
		if (user != null) {
			return DietService.deleteDiet(id);
		} else {
			return new ResponseEntity<>(null,HttpStatus.UNAUTHORIZED);
		}

	}

}
