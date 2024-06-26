package com.example.TableTime.adapter.repository;

import com.example.TableTime.domain.restaurant.TownEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TownRepository extends JpaRepository<TownEntity,Long> {
    Optional<TownEntity> findByName(String name);
    Boolean existsByName(String name);

}