package com.sartop.demoproductsapi.repository;

import com.sartop.demoproductsapi.entity.ClientEntity;
import com.sartop.demoproductsapi.repository.projection.ClientProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ClientRepositoy extends JpaRepository<ClientEntity, Long>
{

    @Query("SELECT c FROM ClientEntity c")
    Page<ClientProjection> findAllPageable(Pageable pageable);

   ClientEntity findByUserId(Long user_id);
}
