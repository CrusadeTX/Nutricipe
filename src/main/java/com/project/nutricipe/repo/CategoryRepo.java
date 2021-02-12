package com.project.nutricipe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.nutricipe.bean.CategoryBean;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryBean, Integer> {

}
