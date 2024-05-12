package com.example.TableTime.service;

import com.example.TableTime.adapter.repository.*;
import com.example.TableTime.adapter.web.adminRest.dto.RestaurantData;
import com.example.TableTime.adapter.web.user.dto.RestaurantList;
import com.example.TableTime.domain.restaurant.*;
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
        var data = new RestaurantData(
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
                restaurant.getMenu().getPhoto()
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
