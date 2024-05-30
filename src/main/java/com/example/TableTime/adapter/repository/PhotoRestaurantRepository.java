package com.example.TableTime.adapter.repository;

import com.example.TableTime.domain.restaurant.photo.PhotoRestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PhotoRestaurantRepository extends JpaRepository<PhotoRestaurantEntity,Long> {
}