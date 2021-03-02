package com.project.nutricipe.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.bean.FridgeBean;
import com.project.nutricipe.bean.ProductBean;
import com.project.nutricipe.repo.FridgeRepo;
import com.project.nutricipe.repo.ProductRepo;
import com.project.nutricipe.repo.UserRepo;
import com.project.nutricipe.security.UserPrincipal;
import com.project.nutricipe.services.FridgeService;

@RestController
public class FridgeController {
	
	private FridgeRepo fridgeRepo;
	private UserRepo userRepo;
	private ProductRepo productRepo;
	//private FridgeService fridgeService = new FridgeService(fridgeRepo, productRepo, userRepo);
	public FridgeController(FridgeRepo fridgeRepo,UserRepo userRepo,ProductRepo productRepo) {
		this.fridgeRepo = fridgeRepo;
		this.userRepo = userRepo;
		this.productRepo = productRepo;
	}

	@GetMapping(path = "/fridge")
	public ResponseEntity<FridgeBean> getUserFridge(@AuthenticationPrincipal UserPrincipal principal) {
		UserBean user = principal.getLoggedInUser();
		if (user != null) {
			FridgeBean fridge = FridgeService.getUserFridge(user);
			if (fridge != null) {
				return new ResponseEntity<>(fridge, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping(path = "/fridge/product")
	public ResponseEntity<Boolean> addProductToFridge(@AuthenticationPrincipal UserPrincipal principal,
			@RequestParam(value = "product_id") int product_id) {
		UserBean user = principal.getLoggedInUser();
		if (user != null) {
			FridgeBean userFridge = user.getFridge();
			Optional<ProductBean> product = productRepo.findById(product_id);
			if (product.isPresent()) {
				Set<ProductBean> products = userFridge.getProducts();
				boolean result = products.add(product.get());
				userFridge.setProducts(products);
				user.setFridge(userFridge);
				userRepo.saveAndFlush(user);
				return new ResponseEntity<>(result, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(false, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
		}
	}

	@DeleteMapping(path = "/fridge/product")
	public ResponseEntity<Boolean> deleteProductFromFridge(@AuthenticationPrincipal UserPrincipal principal,
			@RequestParam(value = "product_id") int product_id) {
		UserBean user = principal.getLoggedInUser();
		if (user != null) {
			boolean result = FridgeService.removeProduct(product_id, user.getId());
			if (result == true) {
				return new ResponseEntity<>(result, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(result, HttpStatus.OK);
			}
		} else {
			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
		}

	}

}
