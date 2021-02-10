package com.project.nutricipe.bean;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="recipe")
public class DietBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="name", nullable=false, unique=true, length = 40)
	private String name;
	@Column(name="reccalories")
	private double recomendedCalories;
	@Column(name="presentcalories")
	private double presentCalories;
	@ManyToMany(mappedBy = "Diets")
	private Set<CategoryBean> Categories;
	@ManyToMany(mappedBy = "Diets")
	private Set<ProductBean> Products;
	@ManyToMany(mappedBy = "Diets")
	private Set<UserBean> Users;
	@ManyToMany(mappedBy = "Diets")
	private Set<RecipeBean> Recipies;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getRecomendedCalories() {
		return recomendedCalories;
	}
	public void setRecomendedCalories(double recomendedCalories) {
		this.recomendedCalories = recomendedCalories;
	}
	public double getPresentCalories() {
		return presentCalories;
	}
	public void setPresentCalories(double presentCalories) {
		this.presentCalories = presentCalories;
	}
	public Set<CategoryBean> getCategories() {
		return Categories;
	}
	public void setCategories(Set<CategoryBean> categories) {
		Categories = categories;
	}
	public Set<ProductBean> getProducts() {
		return Products;
	}
	public void setProducts(Set<ProductBean> products) {
		Products = products;
	}
	public Set<UserBean> getUsers() {
		return Users;
	}
	public void setUsers(Set<UserBean> users) {
		Users = users;
	}
	public Set<RecipeBean> getRecipies() {
		return Recipies;
	}
	public void setRecipies(Set<RecipeBean> recipies) {
		Recipies = recipies;
	}
	
	

}
