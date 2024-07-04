package com.example.TableTime.service;

import com.example.TableTime.adapter.repository.*;
import com.example.TableTime.adapter.web.adminRest.dto.RestaurantData;
import com.example.TableTime.adapter.web.adminRest.dto.ReviewData;
import com.example.TableTime.adapter.web.auth.dto.RestaurantList;
import com.example.TableTime.domain.restaurant.*;
import com.example.TableTime.domain.restaurant.photo.PhotoMenuEntity;
import com.example.TableTime.domain.restaurant.photo.PhotoPlanEntity;
import com.example.TableTime.domain.restaurant.photo.PhotoRestaurantEntity;
import com.example.TableTime.domain.user.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final TownRepository townRepository;
    private final PhotoMenuRepository photoMenuRepository;
    private final PhotoPlanRepository photoPlanRepository;
    private final PhotoRestaurantRepository photoRestaurantRepository;
    private final ReviewRepository reviewRepository;

    public RestaurantEntity findId (Long id) {
        return restaurantRepository.findById(id).get();
    }

    public List<RestaurantList> listRest () {
        var data = restaurantRepository.findByNameNot("Название");
        var list = new ArrayList<RestaurantList>();
        for (var value : data) {
            if (value.getPhotoRest().getPhotoOne() == null) continue;
            var restContext =  new RestaurantList();
            restContext.setId(value.getId_rest());
            restContext.setName(value.getName());
            restContext.setTown(value.getTown().getName());
            restContext.setAddress(value.getAddress());
            restContext.setPhone(value.getPhone());
            restContext.setPhoto(value.getPhotoRest().getPhotoOne());
            if (reviewRepository.findByRestaurant(value).isEmpty()) {
                restContext.setGrade(0);
            }
            else {
                restContext.setGrade(Math.round(reviewRepository.findAvgGrade(value.getId_rest())));
            }
            list.add(restContext);
        }
        return list;
    }

    public RestaurantData getFormRestaurant (RestaurantEntity restaurant) {
        var formatter = DateTimeFormatter.ofPattern("HH:mm");
        var opening = restaurant.getOpening().format(formatter);
        var ending = restaurant.getEnding().format(formatter);

        List<String> photosData = new ArrayList<>();
        photosData.add(restaurant.getPhotoRest().getPhotoOne());
        photosData.add(restaurant.getPhotoRest().getPhotoTwo());
        photosData.add(restaurant.getPhotoRest().getPhotoThree());

        var reviews = reviewRepository.findByRestaurant(restaurant);
        var list = new LinkedList<ReviewData>();
        if (!reviews.isEmpty()) {
            for (var review : reviews) {
                var form = new ReviewData(review.getUser().getUsername(),
                        review.getText(), review.getGrade());
                list.add(form);
            }
        }
        var grade = 0;
        if (reviewRepository.findByRestaurant(restaurant).isEmpty()) {
            grade = 0;
        }
        else {
            grade = Math.round(reviewRepository.findAvgGrade(restaurant.getId_rest()));
        }

        var data = new RestaurantData(
                restaurant.getId_rest(),
                restaurant.getName(),
                restaurant.getTown().getName(),
                restaurant.getAddress(),
                opening,
                ending,
                restaurant.getPhone(),
                restaurant.getDescription(),
                restaurant.getTables().toString(),
                photosData,
                restaurant.getPlan().getPhoto(),
                restaurant.getMenu().getPhoto(),
                list,
                grade
        );
        return data;
    }

    public void createRestaurant (UserEntity user){
        if (restaurantRepository.existsByUser(user))
            throw new UsernameNotFoundException("Пользователь ужя является администратором ресторана");

        existTown("Город");

        var restaurant = new RestaurantEntity();
        restaurant.setName("Название");
        restaurant.setTown(townRepository.findByName("Город").get());
        restaurant.setUser(user);
        restaurant.setAddress("Адрес");
        restaurant.setOpening(LocalTime.of(8, 0));
        restaurant.setEnding(LocalTime.of(20, 0));
        restaurant.setPhone("89000000000");
        restaurant.setDescription("Описание");
        restaurant.setTables(0);
        restaurant.setPhotoRest(savePhotoRest());
        restaurant.setMenu(saveMenu());
        restaurant.setPlan(savePlan());

        restaurantRepository.save(restaurant);
    }

    private PhotoRestaurantEntity savePhotoRest() {
        var photos = new PhotoRestaurantEntity();
        return photoRestaurantRepository.save(photos);
    }
    private PhotoPlanEntity savePlan() {
        var photo = new PhotoPlanEntity();
        return photoPlanRepository.save(photo);
    }

    private PhotoMenuEntity saveMenu() {
        var photo = new PhotoMenuEntity();
        return photoMenuRepository.save(photo);
    }

    public void existTown(String name) {
        if (!townRepository.existsByName(name)) {
            var town = new TownEntity();
            town.setName(name);
            townRepository.save(town);
        }
    }
}
