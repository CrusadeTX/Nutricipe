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
@Table(name="CATEGORY")
@JsonIgnoreProperties({"recipes","diets"})
public class CategoryBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="name", nullable=false, unique=true, length = 255)
	private String name;
	@Column(name="type", nullable=false, unique=false, length = 255)
	private String type;
	@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="RECIPE_CATEGORY",
	joinColumns = @JoinColumn(name="CATEGORY_ID"), 
	inverseJoinColumns = @JoinColumn(name="RECIPE_ID")
	)
	private Set<RecipeBean> recipes;
	@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="DIET_CATEGORY",
	joinColumns = @JoinColumn(name="CATEGORY_ID"), 
	inverseJoinColumns = @JoinColumn(name="DIET_ID")
	)
	private Set<DietBean> diets;
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
	public Set<RecipeBean> getRecipes() {
		return recipes;
	}
	public void setRecipes(Set<RecipeBean> recipes) {
		this.recipes = recipes;
	}
	public Set<DietBean> getDiets() {
		return diets;
	}
	public void setDiets(Set<DietBean> diets) {
		this.diets = diets;
	}

	
}
