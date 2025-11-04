package com.portfolio.maker.repository;

import com.portfolio.maker.entity.Trabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrabajoRepository extends JpaRepository<Trabajo, Integer> {
}