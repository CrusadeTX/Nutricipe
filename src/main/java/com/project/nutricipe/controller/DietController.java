package com.project.nutricipe.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.nutricipe.bean.DietBean;
import com.project.nutricipe.bean.ProductBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.repo.DietRepo;
import com.project.nutricipe.repo.UserRepo;
import com.project.nutricipe.security.UserPrincipal;

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

	@GetMapping(path = "/diet/{id}")
	public DietBean getDietById(@PathVariable int id) {
		Optional<DietBean> diet = dietRepo.findById(id);
		if(diet!=null) {
		if (diet.isPresent()) {
			return diet.get();
		} else {
			return null;
		}

	}
		return null;
	}

}
