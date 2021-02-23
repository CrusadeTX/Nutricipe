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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PreRemove;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="USER")
@JsonIgnoreProperties({"diet","fridge",})
public class UserBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="username", nullable=false, unique=true, length = 255)
	private String username;
	@Column(name="password", nullable=false, length=60)
	private String password;
	@Column(name="email", nullable=false, unique=true, length=255)
	private String email;
    @ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    @JoinTable(name="USER_ROLE",
    		joinColumns = @JoinColumn(name="USER_ID"), 
    		inverseJoinColumns = @JoinColumn(name="ROLE_ID")
    		)
    private Set<RoleBean> roles;
	@ManyToOne()
    private DietBean diet;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "FRIDGE_ID", referencedColumnName = "ID")
	private FridgeBean fridge;
	

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
	public DietBean getDiet() {
		return diet;
	}
	public void addRole(RoleBean role) {
		roles.add(role);
	}
	
	public FridgeBean getFridge() {
		return fridge;
	}
	public void setFridge(FridgeBean fridge) {
		this.fridge = fridge;
	}
	public void setDiet(DietBean diet) {
		this.diet = diet;
	}
	@PreRemove
	public void removeRelations() {
		roles.forEach(role -> role.removeUser(this));
		}
	
}

	
    


