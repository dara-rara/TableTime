package com.example.TableTime.adapter.repository;

import com.example.TableTime.domain.restaurant.PromotionEntity;
import com.example.TableTime.domain.restaurant.RestaurantEntity;
import com.example.TableTime.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<PromotionEntity,Long> {

    List<PromotionEntity> findByRestaurant(RestaurantEntity restaurant);
    Optional<PromotionEntity> findById (Long id);
    boolean existsById(Long id);
    long deleteByRestaurant (RestaurantEntity restaurant);
}
