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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.nutricipe.bean.ProductBean;
import com.project.nutricipe.bean.RecipeBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.repo.ProductRepo;
import com.project.nutricipe.repo.UserRepo;
import com.project.nutricipe.security.UserPrincipal;
import com.project.nutricipe.services.ProductService;
import com.project.nutricipe.services.RecipeService;

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
	@GetMapping(path = "/product/lastfive")
	public List<ProductBean> getLastFiveProducts(@AuthenticationPrincipal UserPrincipal principal) {
		List<ProductBean> result = new ArrayList<ProductBean>();
		List<ProductBean> products;
		UserBean user = principal.getLoggedInUser();
		if (user != null) {
			products = productRepo.findAll();
			if(products.size()<=5) {
				return products;
			}
			else {
				for (int i=0; i<5;i++) {
					result.add(products.get(products.size()-i+1));
				}
				return result;
			}
		} else {
			return null;
		}
	}
	@PostMapping(path="/product/search")
	public ResponseEntity<List<ProductBean>> getProductBySearchString(@RequestParam(value = "searchTerm") String query) {
		//int id=0;
		if(query!=null) {
			 
			
			ResponseEntity<List<ProductBean>> response = ProductService.getProductBySearchString(query.trim());
			return response;
		}
		return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);


	}

}
