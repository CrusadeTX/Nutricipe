package com.project.nutricipe.bean;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="role")
public class RoleBean {
	@Id
	@Column(name="role_id")
	@GeneratedValue
	private int id;
	@Column(name="code", unique=true, nullable=false)
	private String code;
	@Column(name="description", nullable=true)
	private String description;
	@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinTable(name="account_role",
	    		joinColumns = @JoinColumn(name="role_id"), 
	    		inverseJoinColumns = @JoinColumn(name="account_id")
	    		)
	private Set<UserBean> Users;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}