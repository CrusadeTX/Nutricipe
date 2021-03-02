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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name="PRODUCT")
@JsonIgnoreProperties({"recipes","fridges"})
//@JsonIgnoreProperties({"fridges"})
public class ProductBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="NAME", nullable=false, unique=true, length = 40)
	private String name;
	@Column(name="DESCRIPTION", nullable=false, unique=false, length = 255)
	private String description;
	@Column(name="IMAGE_PATH", nullable=false, unique=false, length = 255)
	private String imagePath;
	@Column(name="FATS")
	private double fats;
	@Column(name="PROTEINS")
	private double proteins;
	@Column(name="CARBOHYDRATES")
	private double carbohydrates;
	@Column(name="WEIGHT")
	private double weight;
	@Column(name="CALORIES")
	private double calories;
	@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="product_recipe",
    		joinColumns = @JoinColumn(name="PRODUCT_ID"), 
    		inverseJoinColumns = @JoinColumn(name="RECIPE_ID")
    		)
    private Set<RecipeBean> recipes;
	//@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    //@JoinTable(name="PRODUCT_FRIDGE",
    		//joinColumns = @JoinColumn(name="PRODUCT_ID"), 
    		//inverseJoinColumns = @JoinColumn(name="FRIDGE_ID")
    		//)
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "products")
    private Set<FridgeBean> fridges;
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
	public Set<RecipeBean> getRecipes() {
		return recipes;
	}
	public void setRecipes(Set<RecipeBean> recipes) {
		this.recipes = recipes;
	}
	public Set<FridgeBean> getFridges() {
		return fridges;
	}
	public void setFridges(Set<FridgeBean> fridges) {
		this.fridges = fridges;
	}
	

	
	
}
