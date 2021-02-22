package com.project.nutricipe.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.nutricipe.bean.FridgeBean;

@Repository
public interface FridgeRepo extends JpaRepository<FridgeBean, Integer> {

}
