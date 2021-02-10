package com.project.nutricipe.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.nutricipe.bean.RoleBean;

@Repository
public interface RoleRepo extends JpaRepository<RoleBean, Integer> {
	RoleBean findRoleByCode(String code);

}