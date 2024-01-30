package com.sartop.demoproductsapi.repository;


import com.sartop.demoproductsapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{

}
