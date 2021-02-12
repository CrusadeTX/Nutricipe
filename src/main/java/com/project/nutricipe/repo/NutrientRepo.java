package com.project.nutricipe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.nutricipe.bean.NutrientBean;

@Repository
public interface NutrientRepo extends JpaRepository<NutrientBean, Integer> {

}
