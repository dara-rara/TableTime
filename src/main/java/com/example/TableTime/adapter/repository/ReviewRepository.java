package com.example.TableTime.adapter.repository;

import com.example.TableTime.domain.restaurant.RestaurantEntity;
import com.example.TableTime.domain.restaurant.ReviewEntity;
import com.example.TableTime.domain.restaurant.reserval.ReservalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {

    Optional<ReviewEntity> findByReserval(ReservalEntity reserval);
    List<ReviewEntity> findByRestaurant(RestaurantEntity restaurant);

    @Query(value = "SELECT AVG(r.grade)\n" +
            "FROM Reviews r\n" +
            "WHERE r.id_rest = :id_rest", nativeQuery = true)
    Float findAvgGrade(@Param("id_rest")Long id_rest);

    long deleteByRestaurant (RestaurantEntity restaurant);
}
