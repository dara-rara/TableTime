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


    public PhotoMenuEntity getMenu(UserEntity user) {
        var menu =  photoMenuRepository.findByUser(user);
        if (menu.isEmpty()) throw new UsernameNotFoundException("Пользователь не администратор ресторана");
        return menu.get();
    }

    public PhotoPlanEntity getPlan(UserEntity user) {
        var plan =  photoPlanRepository.findByUser(user);
        if (plan.isEmpty()) throw new UsernameNotFoundException("Пользователь не администратор ресторана");
        return plan.get();
    }

    public PhotoRestaurantEntity getPhotoRest(UserEntity user) {
        var photos =  photoRestaurantRepository.findByUser(user);
        if (photos.isEmpty()) throw new UsernameNotFoundException("Пользователь не администратор ресторана");
        return photos.get();
    }

    public RestaurantEntity getRestaurant (UserEntity user) {
        var restaurant = restaurantRepository.findByUser(user);
        if (restaurant.isEmpty()) throw new UsernameNotFoundException("Пользователь не администратор ресторана");
        return restaurant.get();
    }

    public RestaurantData getFormRestaurant (RestaurantEntity restaurant, PhotoRestaurantEntity photos,
                                             PhotoPlanEntity plan, PhotoMenuEntity menu) {
        var formatter = DateTimeFormatter.ofPattern("HH:mm");
        var opening = restaurant.getOpening().format(formatter);
        var ending = restaurant.getEnding().format(formatter);

        List<String> photosBase64 = new ArrayList<>();
        photosBase64.add(Base64.getEncoder().encodeToString(photos.getPhotoOne()));
        photosBase64.add(Base64.getEncoder().encodeToString(photos.getPhotoTwo()));
        photosBase64.add(Base64.getEncoder().encodeToString(photos.getPhotoThree()));
        var planBase64 = Base64.getEncoder().encodeToString(plan.getPhoto());
        var menuBase64 = Base64.getEncoder().encodeToString(menu.getPhoto());
        var data = new RestaurantData(
                restaurant.getName(),
                restaurant.getTown().getName(),
                restaurant.getAddress(),
                opening,
                ending,
                restaurant.getPhone(),
                restaurant.getDescription(),
                restaurant.getTables().toString(),
                photosBase64,
                planBase64,
                menuBase64
        );
        return data;
    }

    public void updatePhotoRest(UserEntity user, MultipartFile photo1, MultipartFile photo2,
                                MultipartFile photo3) throws IOException {
        var photos = getPhotoRest(user);
        photos.setContentTypeOne(photo1.getContentType());
        photos.setPhotoOne(photo1.getBytes());
        photos.setContentTypeTwo(photo2.getContentType());
        photos.setPhotoTwo(photo2.getBytes());
        photos.setContentTypeThree(photo3.getContentType());
        photos.setPhotoThree(photo3.getBytes());
        photoRestaurantRepository.save(photos);
    }

    public void updatePlan(UserEntity user, MultipartFile photo) throws IOException {
        var plan = getPlan(user);
        plan.setContentType(photo.getContentType());
        plan.setPhoto(photo.getBytes());
        photoPlanRepository.save(plan);
    }
    
    public void updateMenu(UserEntity user, MultipartFile photo) throws IOException {
        var menu = getMenu(user);
        menu.setContentType(photo.getContentType());
        menu.setPhoto(photo.getBytes());
        photoMenuRepository.save(menu);
    }
    
    public void updateRestaurant(UserEntity user, RestaurantInfo form) {
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

    public void createRestaurant (UserEntity user, String photo){
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
        restaurantRepository.save(restaurant);

//        var photoByte = photo.getBytes();
//        var type = photo.getContentType();
        var photoByte = Base64.getDecoder().decode(photo);
        var type = "image/jpg";
        savePhotoRest(user, photoByte, type);
        saveMenu(user, photoByte, type);
        savePlan(user, photoByte, type);
    }

    private void savePhotoRest(UserEntity user, byte[] bytePhoto, String type) {
        var photos = new PhotoRestaurantEntity();
        photos.setUser(user);
        photos.setPhotoOne(bytePhoto);
        photos.setContentTypeOne(type);
        photos.setPhotoTwo(bytePhoto);
        photos.setContentTypeTwo(type);
        photos.setPhotoThree(bytePhoto);
        photos.setContentTypeThree(type);
        photoRestaurantRepository.save(photos);
    }
    private void savePlan(UserEntity user, byte[] bytePhoto, String type) {
        var photo = new PhotoPlanEntity();
        photo.setUser(user);
        photo.setPhoto(bytePhoto);
        photo.setContentType(type);
        photoPlanRepository.save(photo);
    }

    private void saveMenu(UserEntity user, byte[] bytePhoto, String type) {
        var photo = new PhotoMenuEntity();
        photo.setUser(user);
        photo.setPhoto(bytePhoto);
        photo.setContentType(type);
        photoMenuRepository.save(photo);
    }

    private void existTown(String name) {
        if (!townRepository.existsByName(name)) {
            var town = new TownEntity();
            town.setName(name);
            townRepository.save(town);
        }
    }
}
