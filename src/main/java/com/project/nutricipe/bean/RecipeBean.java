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
@Table(name="recipe")
public class RecipeBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="name", nullable=false, unique=true, length = 40)
	private String name;
	@Column(name="description", nullable=false, unique=false, length = 255)
	private String description;
	@Column(name="imagepath", nullable=false, unique=false, length = 255)
	private String imagePath;
	@Column(name="weight")
	private double servingWeight;
	@Column(name="calories")
	private double calories;
	@Column(name="fatpercentage")
	private double fatPercentage;
	@Column(name="proteinpercentage")
	private double proteinPercentage;
	@Column(name="carbPercentage")
	private double carbPercentage;
	@ManyToMany(mappedBy = "Recipies")
	private Set<CategoryBean> Categories;
	@ManyToMany(mappedBy = "Recipies")
	private Set<ProductBean> Products;
	@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="recipe_diet",
    		joinColumns = @JoinColumn(name="recipe_id"), 
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
	public double getServingWeight() {
		return servingWeight;
	}
	public void setServingWeight(double servingWeight) {
		this.servingWeight = servingWeight;
	}
	public double getCalories() {
		return calories;
	}
	public void setCalories(double calories) {
		this.calories = calories;
	}
	public double getFatPercentage() {
		return fatPercentage;
	}
	public void setFatPercentage(double fatPercentage) {
		this.fatPercentage = fatPercentage;
	}
	public double getProteinPercentage() {
		return proteinPercentage;
	}
	public void setProteinPercentage(double proteinPercentage) {
		this.proteinPercentage = proteinPercentage;
	}
	public double getCarbPercentage() {
		return carbPercentage;
	}
	public void setCarbPercentage(double carbPercentage) {
		this.carbPercentage = carbPercentage;
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
	public Set<DietBean> getDiets() {
		return Diets;
	}
	public void setDiets(Set<DietBean> diets) {
		Diets = diets;
	}
	
	
}
	

