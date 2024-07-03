package com.example.TableTime.adapter.repository;

import com.example.TableTime.domain.restaurant.RestaurantEntity;
import com.example.TableTime.domain.restaurant.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<TableEntity,Long> {
    TableEntity findByRestaurantAndNumber(RestaurantEntity restaurant, Integer number);
//    @Query(value = "SELECT t.number FROM tables t WHERE (t.id_rest=:id_rest);", nativeQuery = true)
//    List<Integer> findByAllTable(@Param("id_rest")Long id_rest);

    @Query(value = "SELECT t.number\n" +
            "FROM tables t\n" +
            "WHERE t.id_rest = :id_rest\n" +
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

//    @Query(value = "SELECT t.number\n" +
//            "FROM tables t\n" +
//            "LEFT JOIN reservals r ON t.id_tab = r.id_tab\n" +
//            "AND t.id_rest = :id_rest\n" +
//            "AND r.date = :date\n" +
//            "AND r.state = 'true'\n" +
//            "AND ((:time1 BETWEEN r.time_start AND r.time_end\n" +
//            "OR :time2 BETWEEN r.time_start AND r.time_end)\n" +
//            "OR (:time1 < r.time_start AND :time2 > r.time_end))\n" +
//            "WHERE r.id_tab IS NULL;", nativeQuery = true)
//    List<Integer> findByReservalTable(@Param("id_rest")Long id_rest, @Param("date") Date date,
//                                      @Param("time1") Date time1, @Param("time2") Date time2);

}