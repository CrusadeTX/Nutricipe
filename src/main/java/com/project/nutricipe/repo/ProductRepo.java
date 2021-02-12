package com.project.nutricipe.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.nutricipe.bean.ProductBean;

@Repository
public interface ProductRepo extends JpaRepository<ProductBean, Integer> {

}
