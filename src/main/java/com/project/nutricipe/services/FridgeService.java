package com.project.nutricipe.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.project.nutricipe.bean.FridgeBean;
import com.project.nutricipe.bean.ProductBean;
import com.project.nutricipe.bean.UserBean;
import com.project.nutricipe.repo.FridgeRepo;
import com.project.nutricipe.repo.ProductRepo;
import com.project.nutricipe.repo.UserRepo;

public class FridgeService {
	@Autowired
	private  static FridgeRepo fridgeRepo;
	@Autowired
	private  static ProductRepo productRepo;
	@Autowired
	private  static UserRepo userRepo;
	//public FridgeService (FridgeRepo fridgeRepo,ProductRepo productRepo, UserRepo userRepo) {
		//this.fridgeRepo = fridgeRepo;
		//this.productRepo = productRepo;
		//this.userRepo = userRepo;
	//}
public  static FridgeBean getUserFridge(UserBean user) {
	
	FridgeBean fridge = user.getFridge();
	return fridge;
}	
	

public static  boolean addProduct(int product_id, UserBean user) {
	if(user!=null) {
		FridgeBean userFridge = user.getFridge();
		ProductBean product = productRepo.getOne(product_id);
		if(product!=null) {
		boolean result = userFridge.getProducts().add(product);
		return result;
		}
		return false;
	}
	return false;
		
	
}
public static boolean removeProduct(int product_id, int user_id) {
	UserBean user = userRepo.getOne(user_id);
	FridgeBean userFridge = user.getFridge();
	Optional<ProductBean> product = productRepo.findById(product_id);
	if(product.isPresent()) {
	boolean result = userFridge.getProducts().remove(product.get());
	return result;
	}
	return false;
}
}
