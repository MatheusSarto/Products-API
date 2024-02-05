package com.sartop.demoproductsapi.repository;

import com.sartop.demoproductsapi.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>
{

    Optional<ProductEntity> findByCode(String code);
}
