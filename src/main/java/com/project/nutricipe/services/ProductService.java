package com.project.nutricipe.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.project.nutricipe.bean.ProductBean;
import com.project.nutricipe.bean.RecipeBean;
import com.project.nutricipe.repo.DietRepo;
import com.project.nutricipe.repo.ProductRepo;
import com.project.nutricipe.repo.RecipeRepo;
@Service
public class ProductService {
	@Autowired
	private static DietRepo dietRepo;
	@Autowired
	private static RecipeRepo recipeRepo;
	@Autowired
	private static ProductRepo productRepo;
	
	public ProductService(DietRepo dietRepo, RecipeRepo recipeRepo, ProductRepo productRepo) {
		this.dietRepo = dietRepo;
		this.recipeRepo = recipeRepo;
		this.productRepo = productRepo;
	}
	public  static ResponseEntity<List<ProductBean>> getProductBySearchString(String query){
		List<ProductBean> allProducts = productRepo.findAll();
		List<ProductBean> result = new ArrayList<ProductBean>();
		for(ProductBean product : allProducts) {
			if(product.getName().toLowerCase().contains(query.toLowerCase())) {
				result.add(product);
			}
		}
		if(result.size()>0) {
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		
	}

}
