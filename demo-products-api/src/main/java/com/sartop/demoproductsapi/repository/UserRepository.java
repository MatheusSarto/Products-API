package com.sartop.demoproductsapi.repository;


import com.sartop.demoproductsapi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long>
{
    Optional<UserEntity> findByUsername(String username);

    @Query(value = "SELECT role FROM users WHERE username = ?1", nativeQuery = true)
    Optional<UserEntity.Role> findRoleByUsername(String username);
}
