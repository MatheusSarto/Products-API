package com.sartop.demoproductsapi.repository;

import com.sartop.demoproductsapi.entity.ClientEntity;
import com.sartop.demoproductsapi.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>
{
    List<ProductEntity> findAllByClient(ClientEntity client);

    Optional<ProductEntity> findByIdAndClient(Long id,ClientEntity client);

}
