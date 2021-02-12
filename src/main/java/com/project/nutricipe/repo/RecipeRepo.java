package com.project.nutricipe.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.nutricipe.bean.RecipeBean;
@Repository
public interface RecipeRepo extends JpaRepository<RecipeBean, Integer> {

}
