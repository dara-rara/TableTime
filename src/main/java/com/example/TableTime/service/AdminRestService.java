package com.example.TableTime.service;

import com.example.TableTime.adapter.repository.*;
import com.example.TableTime.adapter.web.adminRest.dto.*;
import com.example.TableTime.adapter.web.adminRest.dto.reserval.RestReservalForm;
import com.example.TableTime.adapter.web.adminRest.dto.reserval.Reserval;
import com.example.TableTime.domain.restaurant.*;
import com.example.TableTime.domain.restaurant.reserval.State;
import com.example.TableTime.domain.restaurant.reserval.TableEntity;
import com.example.TableTime.domain.user.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;


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
    private final TableRepository tableRepository;
    private final UserRepository userRepository;
    private final ReservalRepository reservalRepository;



    public void updatePhotoRest(UserEntity user, String photo1, String photo2,
                                String photo3) {
        var photos = restaurantService.getRestaurant(user).getPhotoRest();
        photos.setPhotoOne(photo1);
        photos.setPhotoTwo(photo2);
        photos.setPhotoThree(photo3);
        photoRestaurantRepository.save(photos);
    }

    public void updatePlan(UserEntity user, String photo) {
        var plan = restaurantService.getRestaurant(user).getPlan();
        plan.setPhoto(photo);
        photoPlanRepository.save(plan);
    }

    public void updateMenu(UserEntity user, String photo) {
        var menu = restaurantService.getRestaurant(user).getMenu();
        menu.setPhoto(photo);
        photoMenuRepository.save(menu);
    }

    public void updateRestaurant(UserEntity user, RestaurantInfo form) {
        restaurantService.existTown(form.town());

        var restaurant = restaurantService.getRestaurant(user);
        restaurant.setName(form.name());
        restaurant.setTown(townRepository.findByName(form.town()).get());
        restaurant.setAddress(form.address());
        restaurant.setOpening(LocalTime.parse(form.opening()));
        restaurant.setEnding(LocalTime.parse(form.ending()));
        restaurant.setPhone(form.phone());
        restaurant.setDescription(form.description());

        restaurantRepository.save(restaurant);
    }

    public void updateTables(UserEntity user, TablesForm form) {
        var restaurant = restaurantService.getRestaurant(user);
        var countForm = form.table();
        var countTable = restaurant.getTables();
        if (countForm != 0 && countTable == 0) {
            for (var i = 1; i <= countForm; i++) {
                var table = new TableEntity();
                table.setRestaurant(restaurant);
                table.setNumber(i);
                table.setState(State.TRUE);
                tableRepository.save(table);
            }

        } else if (countForm != 0 && countTable != 0 && countForm > countTable) {
            for (var i = countTable + 1; i <= countForm; i++) {
                var table = new TableEntity();
                table.setRestaurant(restaurant);
                table.setNumber(i);
                table.setState(State.TRUE);
                tableRepository.save(table);
            }
        } else if (countForm != 0 && countTable != 0 && countForm < countTable) {
            for (var i = countForm + 1; i <= countTable; i++) {
                var table = tableRepository.findByRestaurantAndNumberAndState(
                        restaurant, i, State.TRUE);
                var reservals = reservalRepository.findByTableAndState(table, State.TRUE);
                if (!reservals.isEmpty()) {
                    for (var reserval : reservals) {
                        reservalRepository.delete(reserval);
                    }
                }
                table.setState(State.FALSE);
                tableRepository.save(table);
            }
        }
        restaurant.setTables(countForm);
        restaurantRepository.save(restaurant);
    }

    public AccountRestUpdate updateUser (UserEntity user, AccountRestUpdate accountRestUpdate) {
        user.setUsername(accountRestUpdate.getUsername());
        user.setPhone(accountRestUpdate.getPhone());
        user.setEmail(accountRestUpdate.getEmail());
        userRepository.save(user);
        return accountRestUpdate;
    }

    public RestReservalForm getRestInfoReserval (UserEntity user, RestDateRequest request) throws ParseException {
        var rest = getRestaurantEntity(user);
        var date = new SimpleDateFormat("dd.MM.yyyy").parse(request.date());
        var reservals = reservalRepository
                .findByRestaurantAndDateOrderByTimeStart(
                        rest,
                        date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        LocalDateTime currentDateTime = LocalDateTime.now();
        var dictReserval = new LinkedList<Reserval>();
        if (reservals.isEmpty()) {

        } else {
            for (var reserval : reservals) {
                if (reserval.getState().equals(State.TRUE)) {

                    var datetime = reserval.getDate().atTime(reserval.getTimeEnd());
                    if (currentDateTime.isAfter(datetime)) {
                        reserval.setState(State.FALSE);
                        reservalRepository.save(reserval);
                    }
                }
                var userRes = reserval.getUser();
                var form = new Reserval(reserval.getId_res(), userRes.getRealname(), userRes.getPhone(),
                        DateTimeFormatter.ofPattern("dd.MM.YYYY").format(reserval.getDate()),
                        DateTimeFormatter.ofPattern("HH:mm").format(reserval.getTimeStart()),
                        reserval.getTable().getNumber(),
                        reserval.getPersons(),
                        reserval.getMessage(),
                        reserval.getState());

                dictReserval.add(form);
            }
        }
        return new RestReservalForm(user.getUsername(), user.getEmail(), user.getPhone(), dictReserval);
    }

    private RestaurantEntity getRestaurantEntity(UserEntity user) {
        var restaurant = restaurantRepository.findByUser(user);
        if (restaurant.isEmpty()) throw new RuntimeException("Пользователь не админ ресторана");//переделать!
        return restaurant.get();
    }
}
