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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="DIET")
public class DietBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="NAME", nullable=false, unique=true, length = 255)
	private String name;
	@Column(name="REC_CALORIES")
	private double recomendedCalories;
	@ManyToMany(mappedBy = "diets")
	private Set<CategoryBean> categories;
	@OneToMany(mappedBy = "diet",fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private Set<UserBean> users;
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
	public Set<CategoryBean> getCategories() {
		return categories;
	}
	public void setCategories(Set<CategoryBean> categories) {
		this.categories = categories;
	}
	public Set<UserBean> getUsers() {
		return users;
	}
	public void setUsers(Set<UserBean> users) {
		this.users = users;
	}

	
	

}
