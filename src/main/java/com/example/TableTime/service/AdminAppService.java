package com.example.TableTime.service;

import com.example.TableTime.adapter.repository.*;
import com.example.TableTime.adapter.web.adminApp.dto.RoleList;
import com.example.TableTime.domain.user.Role;
import com.example.TableTime.domain.user.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;


@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminAppService {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final PromotionRepository promotionRepository;
    private final ReservalRepository reservalRepository;
    private final ReviewRepository reviewRepository;
    private final TableRepository tableRepository;

    public UserEntity changeRole (UserEntity userEntity, Role role) {
        userEntity.setRole(role);
        return userRepository.save(userEntity);
    }

    public RoleList getRole () {
        return new RoleList(Arrays.asList(Role.values()));
    }

    public boolean deleteRestaurant(UserEntity user) {
        var restaurantOpt = restaurantRepository.findByUser(user);
        if (restaurantOpt.isEmpty()) return false;
        var restaurant = restaurantOpt.get();
        tableRepository.deleteByRestaurant(restaurant);
        promotionRepository.deleteByRestaurant(restaurant);
        reservalRepository.deleteByRestaurant(restaurant);
        reviewRepository.deleteByRestaurant(restaurant);
        restaurantRepository.delete(restaurant);
        return true;
    }
}
