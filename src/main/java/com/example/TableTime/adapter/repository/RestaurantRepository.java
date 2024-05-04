package com.example.TableTime.adapter.repository;

import com.example.TableTime.domain.restaurant.RestaurantEntity;
import com.example.TableTime.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<RestaurantEntity,Long> {
    Optional<RestaurantEntity> findByUser (UserEntity userEntity);
    boolean existsByUser (UserEntity user);
}
