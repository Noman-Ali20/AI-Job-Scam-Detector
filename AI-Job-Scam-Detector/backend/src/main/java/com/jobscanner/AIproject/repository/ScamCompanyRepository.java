package com.jobscanner.AIproject.repository;



import com.jobscanner.AIproject.entity.ScamCompany;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScamCompanyRepository extends JpaRepository<ScamCompany, Integer> {

    List<ScamCompany> findAll();
}