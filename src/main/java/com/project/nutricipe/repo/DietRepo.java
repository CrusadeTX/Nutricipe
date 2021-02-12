package com.project.nutricipe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.nutricipe.bean.DietBean;

@Repository
public interface DietRepo extends JpaRepository<DietBean, Integer> {

}
