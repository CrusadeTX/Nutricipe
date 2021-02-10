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
@Table(name="nutrient")
public class NutrientBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="name", nullable=false, unique=true, length = 40)
	private String name;
	@Column(name="description", nullable=false, unique=true, length = 255)
	private String description;
	@Column(name="ammount", nullable=false, unique=false)
	private double ammount;
	@Column(name="imagePath", nullable=false, unique=true, length = 40)
	private String imagePath;
	@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="product_nutrient",
    		joinColumns = @JoinColumn(name="nutrient_id"), 
    		inverseJoinColumns = @JoinColumn(name="product_id")
    		)
    private Set<ProductBean> Products;
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
	public double getAmmount() {
		return ammount;
	}
	public void setAmmount(double ammount) {
		this.ammount = ammount;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Set<ProductBean> getProducts() {
		return Products;
	}
	public void setProducts(Set<ProductBean> products) {
		Products = products;
	}
	
}
