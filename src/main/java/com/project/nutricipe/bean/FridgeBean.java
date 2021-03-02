package com.project.nutricipe.bean;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.OneToOne;

@Entity
@Table(name="FRIDGE")
//@JsonIgnoreProperties({"products","user"})
@JsonIgnoreProperties({"user"})

public class FridgeBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="TOTAL_CALORIES")
	private double totalCalories;
	//@ManyToMany(fetch = FetchType.EAGER, mappedBy = "fridges")
	@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.PERSIST)
    @JoinTable(name="PRODUCT_FRIDGE",
    		joinColumns = @JoinColumn(name="FRIDGE_ID"), 
    		inverseJoinColumns = @JoinColumn(name="PRODUCT_ID")
    		)
	private Set<ProductBean> products;
	@OneToOne(mappedBy = "fridge")
	private UserBean user;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getTotalCalories() {
		return totalCalories;
	}
	public void setTotalCalories(double totalCalories) {
		this.totalCalories = totalCalories;
	}
	public Set<ProductBean> getProducts() {
		return products;
	}
	public void setProducts(Set<ProductBean> products) {
		this.products = products;
	}
	public UserBean getUser() {
		return user;
	}
	public void setUser(UserBean user) {
		this.user = user;
	}
	
	
}
