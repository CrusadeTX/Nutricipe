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
@Table(name="product")
public class ProductBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="name", nullable=false, unique=true, length = 40)
	private String name;
	@Column(name="description", nullable=false, unique=false, length = 255)
	private String description;
	@Column(name="fatpercentage")
	private double fatPercentage;
	@Column(name="proteinpercentage")
	private double proteinPercentage;
	@Column(name="carbPercentage")
	private double carbPercentage;
	@Column(name="weight")
	private double servingWeight;
	@Column(name="calories")
	private double calories;
	@Column(name="imgpath")
	private String imgPath;
	@ManyToMany(mappedBy = "Products")
	private Set<NutrientBean> Nutrients;
	@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="product_recipe",
    		joinColumns = @JoinColumn(name="product_id"), 
    		inverseJoinColumns = @JoinColumn(name="recipe_id")
    		)
    private Set<RecipeBean> Recipies;
	@ManyToMany(mappedBy = "Products")
	private Set<CategoryBean> Categories;
	@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="product_diet",
    		joinColumns = @JoinColumn(name="product_id"), 
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
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public Set<NutrientBean> getNutrients() {
		return Nutrients;
	}
	public void setNutrients(Set<NutrientBean> nutrients) {
		Nutrients = nutrients;
	}
	public Set<RecipeBean> getRecipies() {
		return Recipies;
	}
	public void setRecipies(Set<RecipeBean> recipies) {
		Recipies = recipies;
	}
	public Set<CategoryBean> getCategories() {
		return Categories;
	}
	public void setCategories(Set<CategoryBean> categories) {
		Categories = categories;
	}
	public Set<DietBean> getDiets() {
		return Diets;
	}
	public void setDiets(Set<DietBean> diets) {
		Diets = diets;
	}
	
	
}
