package com.example.TableTime.adapter.repository;

import com.example.TableTime.domain.restaurant.PhotoRestaurantEntity;
import com.example.TableTime.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PhotoRestaurantRepository extends JpaRepository<PhotoRestaurantEntity,Long> {
}