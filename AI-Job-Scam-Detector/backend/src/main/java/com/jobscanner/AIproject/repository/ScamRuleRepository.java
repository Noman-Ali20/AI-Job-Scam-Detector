package com.jobscanner.AIproject.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.jobscanner.AIproject.entity.ScamRule;

import java.util.List;

public interface ScamRuleRepository extends JpaRepository<ScamRule, Integer> {

    List<ScamRule> findAll();
}