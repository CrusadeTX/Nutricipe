package com.project.nutricipe.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.project.nutricipe.bean.ProductBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.repo.ProductRepo;
import com.project.nutricipe.repo.UserRepo;
import com.project.nutricipe.security.UserPrincipal;

@RestController
public class ProductController {
	@Autowired
	private ProductRepo productRepo;
	private UserRepo userRepo;

	public ProductController(ProductRepo productRepo, UserRepo userRepo) {
		this.productRepo = productRepo;
		this.userRepo = userRepo;
	}

	@GetMapping(path = "/product/all")
	public List<ProductBean> getAllProducts(@AuthenticationPrincipal UserPrincipal principal) {
		UserBean user = principal.getLoggedInUser();
		if (user != null) {
			return productRepo.findAll();
		} else {
			return null;
		}
	}

	@GetMapping(path = "/product/{id}")
	public ProductBean getProductById(@PathVariable int id) {
		Optional<ProductBean> product = productRepo.findById(id);
		if (product.isPresent()) {
			return product.get();
		} else {
			return null;
		}
	}

}
