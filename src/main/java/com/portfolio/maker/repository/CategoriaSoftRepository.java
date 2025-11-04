package com.portfolio.maker.repository;

import com.portfolio.maker.entity.CategoriaSoft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaSoftRepository extends JpaRepository<CategoriaSoft, Integer> {
}