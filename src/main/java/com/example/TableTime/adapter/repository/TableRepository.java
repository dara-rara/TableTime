package com.example.TableTime.adapter.repository;

import com.example.TableTime.domain.restaurant.RestaurantEntity;
import com.example.TableTime.domain.restaurant.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface TableRepository extends JpaRepository<TableEntity,Long> {
    TableEntity findByRestaurantAndNumber(RestaurantEntity restaurant, Integer number);
    @Query(value = "SELECT t.number FROM tables t WHERE (t.id_rest=:id_rest);", nativeQuery = true)
    List<Integer> findByAllTable(@Param("id_rest")Long id_rest);

    @Query(value = "SELECT t.number\n" +
            "FROM tables t\n" +
            "INNER JOIN reservals r ON t.id_tab = r.id_tab\n" +
            "WHERE t.id_rest = :id_rest\n" +
            "AND r.date = :date\n" +
            "AND r.time_start > :time1 AND r.time_start < :time2 \n" +
            "AND r.state = true;", nativeQuery = true)
    List<Integer> findByReservalTable(@Param("id_rest")Long id_rest, @Param("date")LocalDate date,
                                      @Param("time1") Date time1, @Param("time2") Date time2);

//    @Query(value = "SELECT t.table\n" +
//                    "FROM tables t\n" +
//                    "WHERE t.id_rest = :id_rest\n" +
//                    "AND t.id IN (\n" +
//                    "    SELECT r.id_table\n" +
//                    "    FROM reservals r\n" +
//                    "    WHERE r.date = :date\n" +
//                    "    AND NOT(r.time BETWEEN :time1 AND :time2) \n" +
//                    "    AND r.state = false\n" +
//                    ");",
//            nativeQuery = true)
//    List<Integer> findByTable(@Param("id_rest")Long id_rest, @Param("date")LocalDate date,
//                              @Param("time1")LocalTime time1, @Param("time2") LocalTime time2);
}