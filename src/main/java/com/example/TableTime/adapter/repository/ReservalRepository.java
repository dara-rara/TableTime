package com.example.TableTime.adapter.repository;

import com.example.TableTime.domain.restaurant.ReservalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservalRepository extends JpaRepository<ReservalEntity,Long> {

}
