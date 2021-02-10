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
@Table(name="user")
@JsonIgnoreProperties({"quotes","posts","roles"})
public class UserBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="username", nullable=false, unique=true, length = 40)
	private String username;
	@Column(name="password", nullable=false, length=60)
	private String password;
	@Column(name="email", nullable=false, unique=true, length=256)
	private String email;
    @ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="account_role",
    		joinColumns = @JoinColumn(name="account_id"), 
    		inverseJoinColumns = @JoinColumn(name="role_id")
    		)
    private Set<RoleBean> roles;
	@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="user_category",
    		joinColumns = @JoinColumn(name="account_id"), 
    		inverseJoinColumns = @JoinColumn(name="category_id")
    		)
    private Set<CategoryBean> categories;
	@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="user_diet",
    		joinColumns = @JoinColumn(name="user_id"), 
    		inverseJoinColumns = @JoinColumn(name="diet_id")
    		)
    private Set<DietBean> Diets;
	

    public UserBean() {};
    public UserBean(String username, String email) {
    	this.username = username;
    	this.email = email;
    	
    };
    public UserBean(String username, String password, String email ) {
    	this.username = username;
    	this.password = password;
    	this.email = email;
    	
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<RoleBean> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleBean> roles) {
		this.roles = roles;
	}
	public Set<CategoryBean> getCategories() {
		return categories;
	}
	public void setCategories(Set<CategoryBean> categories) {
		this.categories = categories;
	}
	public Set<DietBean> getDiets() {
		return Diets;
	}
	public void setDiets(Set<DietBean> diets) {
		Diets = diets;
	};
	
    

}
