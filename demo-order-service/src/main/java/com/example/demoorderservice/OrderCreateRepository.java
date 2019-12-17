package com.example.demoorderservice;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCreateRepository extends JpaRepository<OrderCreateDTO, BigDecimal> {

}
