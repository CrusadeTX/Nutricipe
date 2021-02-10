package com.project.nutricipe.bean;

import java.util.List;
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

@Entity
@Table(name="category")
public class CategoryBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="name", nullable=false, unique=true, length = 40)
	private String name;
	@Column(name="type", nullable=false, unique=false, length = 40)
	private String type;
	@Column(name="description", nullable=false, unique=false, length = 255)
	private String description;
	@ManyToMany(mappedBy = "categories")
	private Set<UserBean> users;
	@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="product_category",
	joinColumns = @JoinColumn(name="category_id"), 
	inverseJoinColumns = @JoinColumn(name="product_id")
	)
	private Set<ProductBean> Products;
	@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="recipe_category",
	joinColumns = @JoinColumn(name="category_id"), 
	inverseJoinColumns = @JoinColumn(name="recipe_id")
	)
	private Set<RecipeBean> Recipies;
	@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="diet_category",
	joinColumns = @JoinColumn(name="category_id"), 
	inverseJoinColumns = @JoinColumn(name="diet_id")
	)
	private Set<DietBean> Diets;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<UserBean> getUsers() {
		return users;
	}
	public void setUsers(Set<UserBean> users) {
		this.users = users;
	}
	public Set<ProductBean> getProducts() {
		return Products;
	}
	public void setProducts(Set<ProductBean> products) {
		Products = products;
	}
	public Set<RecipeBean> getRecipies() {
		return Recipies;
	}
	public void setRecipies(Set<RecipeBean> recipies) {
		Recipies = recipies;
	}
	public Set<DietBean> getDiets() {
		return Diets;
	}
	public void setDiets(Set<DietBean> diets) {
		Diets = diets;
	}
	
}
