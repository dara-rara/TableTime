package com.example.TableTime.adapter.repository;

import com.example.TableTime.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUsername(String username);

    @Query(value = "SELECT COUNT(r.id_res) FROM Reservals r;", nativeQuery = true)
    Long countValues();

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

}

