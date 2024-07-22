package com.example.TableTime.adapter.repository;

import com.example.TableTime.domain.restaurant.RestaurantEntity;
import com.example.TableTime.domain.restaurant.reserval.State;
import com.example.TableTime.domain.restaurant.reserval.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<TableEntity,Long> {
    TableEntity findByRestaurantAndNumberAndState(RestaurantEntity restaurant, Integer number, State state);

    @Query(value = "SELECT t.number\n" +
            "FROM tables t\n" +
            "WHERE t.id_rest = :id_rest\n" +
            "AND t.state = 'TRUE'\n" +
            "AND t.id_tab NOT IN (\n" +
            "    SELECT r.id_tab\n" +
            "    FROM reservals_rest r\n" +
            "    WHERE r.date = :date\n" +
            "    AND r.state = 'TRUE'\n" +
            "    AND ((:time1 BETWEEN r.time_start AND r.time_end\n" +
            "    OR :time2 BETWEEN r.time_start AND r.time_end)\n" +
            "    OR (:time1 < r.time_start AND :time2 > r.time_end))\n" +
            ") ORDER BY t.number ASC", nativeQuery = true)
    List<Integer> findByNotReservalTable(@Param("id_rest")Long id_rest, @Param("date") Date date,
                                         @Param("time1") Date time1, @Param("time2") Date time2);

}