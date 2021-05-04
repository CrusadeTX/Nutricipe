package com.project.nutricipe.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
import org.springframework.web.multipart.MultipartFile;

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
	@PostMapping(path="/product/upload")
	public String uploadProductImage(@RequestParam(value = "image") MultipartFile file) {
		//int id=0;
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
	@PostMapping(path="/product")
	public ResponseEntity<ProductBean> createProduct(@AuthenticationPrincipal UserPrincipal principal, @RequestParam(value="name") String name, @RequestParam(value="description") String description,
			@RequestParam(value="imagePath") String imagePath, @RequestParam(value="calories") String calories, @RequestParam(value="weight") String weight, @RequestParam(value="carbohydrates") String carbs,
			@RequestParam(value="proteins") String proteins, @RequestParam(value="fats") String fats){
		UserBean user = principal.getLoggedInUser();
		if (user != null) {
			ResponseEntity<ProductBean> result = ProductService.createProduct(name, description, imagePath, calories, weight, carbs, proteins, fats);
			return result;
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		
	}
	@DeleteMapping(path="/product/{id}")
	public ResponseEntity<Boolean> createProduct(@AuthenticationPrincipal UserPrincipal principal, @PathVariable int id){
		UserBean user = principal.getLoggedInUser();
		if (user != null) {
			ResponseEntity<Boolean> result = ProductService.deleteProduct(id);
			return result;
		}
		else {
			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
		}
		
		
	}
	@PutMapping(path="/product")
	public ResponseEntity<ProductBean> updateProduct(@AuthenticationPrincipal UserPrincipal principal, @RequestParam(value="name") String name, @RequestParam(value="description") String description,
			@RequestParam(value="imagePath") String imagePath, @RequestParam(value="calories") String calories, @RequestParam(value="weight") String weight, @RequestParam(value="carbohydrates") String carbs,
			@RequestParam(value="proteins") String proteins, @RequestParam(value="fats") String fats, @RequestParam(value="id") String id){
		UserBean user = principal.getLoggedInUser();
		if (user != null) {
			ResponseEntity<ProductBean> result = ProductService.updateProduct(id,name, description, imagePath, calories, weight, carbs, proteins, fats);
			return result;
		}
		else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
		
		
	}
}
