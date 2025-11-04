package com.portfolio.maker.repository;

import com.portfolio.maker.entity.AcercaDe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcercaDeRepository extends JpaRepository<AcercaDe, Integer> {
}