package com.portfolio.maker.repository;

import com.portfolio.maker.entity.HabilidadSoft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HabilidadSoftRepository extends JpaRepository<HabilidadSoft, Integer> {
}