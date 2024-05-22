package com.example.TableTime.adapter.repository;

import com.example.TableTime.domain.restaurant.photo.PhotoPlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoPlanRepository extends JpaRepository<PhotoPlanEntity,Long> {
}

