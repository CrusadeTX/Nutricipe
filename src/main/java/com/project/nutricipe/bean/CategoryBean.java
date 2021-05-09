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
@Table(name="CATEGORY")
@JsonIgnoreProperties({"recipes","diets"})
public class CategoryBean implements Comparable<CategoryBean> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="name", nullable=false, unique=true, length = 255)
	private String name;
	@Column(name="type", nullable=false, unique=false, length = 255)
	private String type;
	//@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    //@JoinTable(name="RECIPE_CATEGORY",
	//joinColumns = @JoinColumn(name="CATEGORY_ID"), 
	//inverseJoinColumns = @JoinColumn(name="RECIPE_ID")
	//)
	@ManyToMany(mappedBy = "categories")
	private Set<RecipeBean> recipes;
	//@ManyToMany( fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    //@JoinTable(name="DIET_CATEGORY",
	//joinColumns = @JoinColumn(name="CATEGORY_ID"), 
	//inverseJoinColumns = @JoinColumn(name="DIET_ID")
	//)
	@ManyToMany(mappedBy = "categories", fetch = FetchType.EAGER)
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
	@Override
    public boolean equals(Object o) { 

        // If the object is compared with itself then return true
        if (o == this) { 
            return true; 
        } 

        /* Check if o is an instance of Complex or not 
          "null instanceof [type]" also returns false */
        if (!(o instanceof CategoryBean)) { 
            return false; 
        } 

        // typecast o to Complex so that we can compare data members
        CategoryBean c = (CategoryBean) o; 

        // Compare the data members and return accordingly
        return this.getId()==c.getId(); 
    }
	@Override
	public int compareTo(CategoryBean category) {
		if (this.getId() == 0 || category.getId() == 0) { 
		      return 0; 
		    } 
		    return Integer.compare(this.getId(), category.getId());
		  }
	@PreRemove
	public void removeRelations() {
		diets = null;
		recipes = null;
		}
	public void removeDiet(DietBean diet) {
		if(diets.contains(diet)) {
			diets.remove(diet);
		}
	}
	public void removeRecipe(RecipeBean recipe) {
		if(recipes.contains(recipe)) {
			recipes.remove(recipe);
			
		}
		
	}
	

	
}
