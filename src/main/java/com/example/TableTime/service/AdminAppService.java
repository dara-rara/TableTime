package com.example.TableTime.service;

import com.example.TableTime.adapter.repository.RestaurantRepository;
import com.example.TableTime.adapter.repository.TownRepository;
import com.example.TableTime.adapter.repository.UserRepository;
import com.example.TableTime.adapter.web.adminApp.dto.RoleList;
import com.example.TableTime.domain.restaurant.RestaurantEntity;
import com.example.TableTime.domain.user.Role;
import com.example.TableTime.domain.user.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final TownRepository townRepository;
    public UserEntity getUser (String email) {
        var user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        return user.get();
    }

    public UserEntity changeRole (UserEntity userEntity, Role role) {
        userEntity.setRole(role);
        return userRepository.save(userEntity);
    }

    public RestaurantEntity createRestaurant (UserEntity userEntity) {

        var restaurant = new RestaurantEntity();
        restaurant.setName("Название");
        restaurant.setTown(townRepository.findByName("Город").get());
        restaurant.setUser(userEntity);
        restaurant.setAddress("Адрес");
        restaurant.setOpening("08:00");
        restaurant.setEnding("20:00");
        restaurant.setPhone("89000000000");
        restaurant.setDescription("Описание");
        return restaurantRepository.save(restaurant);
    }

    public RestaurantEntity getRestaurant (UserEntity userEntity) {
        return restaurantRepository.findByUser(userEntity).get();
    }

    public RoleList getRole () {
        return new RoleList(Arrays.asList(Role.values()));
    }
}
