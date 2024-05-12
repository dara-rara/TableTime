package com.example.TableTime.adapter.repository;

import com.example.TableTime.domain.restaurant.PhotoMenuEntity;
import com.example.TableTime.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoMenuRepository extends JpaRepository<PhotoMenuEntity,Long> {
}
