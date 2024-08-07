package com.example.TableTime.adapter.repository;

import com.example.TableTime.domain.restaurant.reserval.ReservalEntity;
import com.example.TableTime.domain.restaurant.RestaurantEntity;
import com.example.TableTime.domain.restaurant.reserval.State;
import com.example.TableTime.domain.restaurant.reserval.TableEntity;
import com.example.TableTime.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservalRepository extends JpaRepository<ReservalEntity,Long> {

    List<ReservalEntity> findByUserOrderByDate(UserEntity user);
    List<ReservalEntity> findByRestaurantAndDateOrderByTimeStart(RestaurantEntity restaurant,
                                                                 LocalDate date);

    Optional<ReservalEntity> findByUserAndDateAndTimeStart(UserEntity user, LocalDate date, LocalTime time);
    Optional<ReservalEntity> findById(Long id);

    List<ReservalEntity> findByTableAndState(TableEntity table, State state);

    long deleteByRestaurant (RestaurantEntity restaurant);
}
