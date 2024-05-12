package com.example.TableTime.adapter.repository;

import com.example.TableTime.domain.restaurant.PhotoPlanEntity;
import com.example.TableTime.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoPlanRepository extends JpaRepository<PhotoPlanEntity,Long> {
}

