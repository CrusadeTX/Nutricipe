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
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="RECIPE")
//@JsonIgnoreProperties({"categories","products"})
//@JsonIgnoreProperties({"products"})
public class RecipeBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="NAME", nullable=false, unique=true, length = 40)
	private String name;
	@Column(name="DESCRIPTION", nullable=false, unique=false, length = 255)
	private String description;
	@Column(name="IMAGE_PATH", nullable=false, unique=false, length = 255)
	private String imagePath;
	@Column(name="WEIGHT")
	private double weight;
	@Column(name="CALORIES")
	private double calories;
	@Column(name="FATS")
	private double fats;
	@Column(name="PROTEINS")
	private double proteins;
	@Column(name="CARBOHYDRATES")
	private double carbohydrates;
	@Column(name="AUTHOR_ID")
	private int authorId;
	//@ManyToMany(mappedBy = "recipes")
	@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="RECIPE_CATEGORY",
	joinColumns = @JoinColumn(name="RECIPE_ID"), 
	inverseJoinColumns = @JoinColumn(name="CATEGORY_ID")
	)
	private Set<CategoryBean> categories;
	//@ManyToMany(mappedBy = "recipes")
	//private Set<ProductBean> products;
	@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="RECIPE_PRODUCT",
    		joinColumns = @JoinColumn(name="RECIPE_ID"), 
    		inverseJoinColumns = @JoinColumn(name="PRODUCT_ID")
    		)
    private Set<ProductBean> products;
	@PreRemove
	public void removeRelations() {
		products.forEach(product ->product.removeRecipe(this));
		products =null;
		categories =null;
		}
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public double getCalories() {
		return calories;
	}
	public void setCalories(double calories) {
		this.calories = calories;
	}
	public double getFats() {
		return fats;
	}
	public void setFats(double fats) {
		this.fats = fats;
	}
	public double getProteins() {
		return proteins;
	}
	public void setProteins(double proteins) {
		this.proteins = proteins;
	}
	public double getCarbohydrates() {
		return carbohydrates;
	}
	public void setCarbohydrates(double carbohydrates) {
		this.carbohydrates = carbohydrates;
	}
	public Set<CategoryBean> getCategories() {
		return categories;
	}
	public void setCategories(Set<CategoryBean> categories) {
		this.categories = categories;
	}
	public Set<ProductBean> getProducts() {
		return products;
	}
	public void setProducts(Set<ProductBean> products) {
		this.products = products;
	}
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public void removeProduct(ProductBean product) {
		if(products.contains(product)) {
			products.remove(product);
			
		}
		
	}
	public void removeCategory(CategoryBean category) {
		if(categories.contains(category)) {
			categories.remove(category);
			
		}
		
	}
	
}
	

