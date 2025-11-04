package com.portfolio.maker.repository;

import com.portfolio.maker.entity.HabilidadHard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabilidadHardRepository extends JpaRepository<HabilidadHard, Integer> {
}