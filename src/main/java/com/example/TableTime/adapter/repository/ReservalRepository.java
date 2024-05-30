package com.example.TableTime.adapter.repository;

import com.example.TableTime.domain.restaurant.ReservalEntity;
import com.example.TableTime.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReservalRepository extends JpaRepository<ReservalEntity,Long> {

    List<ReservalEntity> findByUser (UserEntity userEntity);
}
