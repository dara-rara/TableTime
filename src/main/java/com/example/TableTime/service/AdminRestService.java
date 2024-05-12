package com.example.TableTime.service;

import com.example.TableTime.adapter.repository.*;
import com.example.TableTime.adapter.web.adminRest.dto.RestaurantData;
import com.example.TableTime.adapter.web.adminRest.dto.RestaurantInfo;
import com.example.TableTime.domain.restaurant.*;
import com.example.TableTime.domain.user.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AdminRestService {

    private final RestaurantRepository restaurantRepository;
    private final TownRepository townRepository;
    private final PhotoMenuRepository photoMenuRepository;
    private final PhotoPlanRepository photoPlanRepository;
    private final PhotoRestaurantRepository photoRestaurantRepository;
    private final RestaurantService restaurantService;

    public RestaurantEntity getRestaurant(UserEntity user) {
        var restaurant = restaurantRepository.findByUser(user);
        if (restaurant.isEmpty()) throw new UsernameNotFoundException("Пользователь не администратор ресторана");
        return restaurant.get();
    }


    public void updatePhotoRest(UserEntity user, String photo1, String photo2,
                                String photo3) {
        var photos = getRestaurant(user).getPhotoRest();
        photos.setPhotoOne(photo1);
        photos.setPhotoTwo(photo2);
        photos.setPhotoThree(photo3);
        photoRestaurantRepository.save(photos);
    }

    public void updatePlan(UserEntity user, String photo) {
        var plan = getRestaurant(user).getPlan();
        plan.setPhoto(photo);
        photoPlanRepository.save(plan);
    }

    public void updateMenu(UserEntity user, String photo) {
        var menu = getRestaurant(user).getMenu();
        menu.setPhoto(photo);
        photoMenuRepository.save(menu);
    }

    public void updateRestaurant(UserEntity user, RestaurantInfo form) {
        restaurantService.existTown(form.town());

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
}
