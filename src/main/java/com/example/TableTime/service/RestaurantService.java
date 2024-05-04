package com.example.TableTime.service;

import com.example.TableTime.adapter.repository.RestaurantRepository;
import com.example.TableTime.adapter.repository.TownRepository;
import com.example.TableTime.adapter.web.restaurant.dto.RestaurantForm;
import com.example.TableTime.domain.restaurant.RestaurantEntity;
import com.example.TableTime.domain.restaurant.TownEntity;
import com.example.TableTime.domain.user.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final TownRepository townRepository;

    public RestaurantEntity getRestaurant (UserEntity user) {
        var restaurant = restaurantRepository.findByUser(user);
        if (restaurant.isEmpty()) throw new UsernameNotFoundException("Пользователь не администратор ресторана");
        return restaurant.get();
    }

    public RestaurantForm getFormRestaurant (RestaurantEntity restaurant) {
        var formatter = DateTimeFormatter.ofPattern("HH:mm");
        var opening = restaurant.getOpening().format(formatter);
        var ending = restaurant.getEnding().format(formatter);
        var form = new RestaurantForm(
                restaurant.getName(),
                restaurant.getTown().getName(),
                restaurant.getAddress(),
                opening,
                ending,
                restaurant.getPhone(),
                restaurant.getDescription(),
                restaurant.getTables().toString()
        );
        return form;
    }
    public void updateRestaurant(UserEntity user, RestaurantForm form) {
        existTown(form.town());

        var restaurant = getRestaurant(user);
        restaurant.setName(form.name());
        restaurant.setTown(townRepository.findByName(form.town()).get());
        restaurant.setAddress(form.address());
        restaurant.setOpening(LocalTime.parse(form.opening()));
        restaurant.setEnding(LocalTime.parse(form.ending()));
        restaurant.setPhone(form.phone());
        restaurant.setDescription(form.description());
        restaurant.setTables(Integer.parseInt(form.tables()));
        restaurantRepository.save(restaurant);
    }

    public void createRestaurant (UserEntity userEntity) {
        if (restaurantRepository.existsByUser(userEntity))
            throw new UsernameNotFoundException("Пользователь ужя является администратором ресторана");

        existTown("Город");

        var restaurant = new RestaurantEntity();
        restaurant.setName("Название");
        restaurant.setTown(townRepository.findByName("Город").get());
        restaurant.setUser(userEntity);
        restaurant.setAddress("Адрес");
        restaurant.setOpening(LocalTime.of(8, 0));
        restaurant.setEnding(LocalTime.of(20, 0));
        restaurant.setPhone("89000000000");
        restaurant.setDescription("Описание");
        restaurant.setTables(0);
        restaurantRepository.save(restaurant);
    }

    private void existTown(String name) {
        if (!townRepository.existsByName(name)) {
            var town = new TownEntity();
            town.setName(name);
            townRepository.save(town);
        }
    }
}
