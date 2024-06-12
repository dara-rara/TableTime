package com.example.TableTime.service;

import com.example.TableTime.adapter.repository.ReservalRepository;
import com.example.TableTime.adapter.repository.RestaurantRepository;
import com.example.TableTime.adapter.repository.TableRepository;
import com.example.TableTime.adapter.repository.UserRepository;
import com.example.TableTime.adapter.web.adminRest.dto.reserval.ReservalRest;
import com.example.TableTime.adapter.web.adminRest.dto.reserval.ReservalRestFreeTable;
import com.example.TableTime.adapter.web.adminRest.dto.reserval.ReservalRestRequest;
import com.example.TableTime.adapter.web.adminRest.dto.reserval.RestReservalAndTableForm;
import com.example.TableTime.adapter.web.user.dto.*;
import com.example.TableTime.domain.restaurant.ReservalEntity;
import com.example.TableTime.domain.user.Role;
import com.example.TableTime.domain.user.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReservalService {
    private final TableRepository tableRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReservalRepository reservalRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public ReservalRequest getFreeTablesUser(ReservalForm form, Long id) throws ParseException {
        var resTables = tableRepository.findByNotReservalTable(id,
                new SimpleDateFormat("dd.MM.yyyy").parse(form.date()),
                new SimpleDateFormat("HH:mm").parse(form.timeStart()),
                new SimpleDateFormat("HH:mm").parse(form.timeEnd()));
        return new ReservalRequest(form.date(), form.timeStart(), form.timeEnd(), form.persons(), form.message(), resTables);
    }

    public ReservalRestRequest getFreeTablesRest(ReservalRestFreeTable form, Long id) throws ParseException {
        var resTables = tableRepository.findByNotReservalTable(id,
                new SimpleDateFormat("dd.MM.yyyy").parse(form.date()),
                new SimpleDateFormat("HH:mm").parse(form.timeStart()),
                new SimpleDateFormat("HH:mm").parse(form.timeEnd()));
        return new ReservalRestRequest(form.name(), form.phone(), form.date(),
                form.timeStart(), form.timeEnd(), form.persons(), form.message(), resTables);
    }

    public UserEntity createUnregistUser(String name, String phone) {
        var user = new UserEntity();
        user.setPhone(phone);
        user.setRealname(name);
        user.setRole(Role.USER);
        var id = userRepository.countValues();
        user.setUsername("client" + id.toString());
        return userRepository.save(user);
    }
    public void createReservalRest(RestReservalAndTableForm form, Long id, UserEntity user) throws ParseException {
        var reserval = new ReservalAndTableForm(form.date(), form.timeStart(),
                form.timeEnd(), form.persons(), form.message(), form.table());
        createReserval(reserval, id, user);
    }

    public void createReserval(ReservalAndTableForm form, Long id, UserEntity user) throws ParseException {
        var restaurant = restaurantRepository.getReferenceById(id);
        var table = tableRepository.findByRestaurantAndNumber(
                restaurant, form.table());
        var reserval = new ReservalEntity();
        reserval.setUser(user);
        reserval.setRestaurant(restaurant);
        reserval.setTable(table);
        reserval.setPersons(form.persons());
        reserval.setTimeStart(LocalTime.parse(form.timeStart()));
        reserval.setTimeEnd(LocalTime.parse(form.timeEnd()));
        var date = new SimpleDateFormat("dd.MM.yyyy").parse(form.date());
        reserval.setDate(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
//        reserval.setTimeStart(new SimpleDateFormat("HH:mm").parse(form.timeStart()));
//        reserval.setTimeEnd(new SimpleDateFormat("HH:mm").parse(form.timeEnd()));
//        reserval.setDate(new SimpleDateFormat("dd.MM.yyyy").parse(form.date()));
        reserval.setState("true");
        reserval.setMessage(form.message());
        reservalRepository.save(reserval);
    }

    public void cancelReservalUser(UserEntity user, ReservalUser userReserval) throws ParseException {
        var date = new SimpleDateFormat("dd.MM.yyyy").parse(userReserval.date());
        var reserval = reservalRepository
                .findByUserAndDateAndTimeStart(user,
                        date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        LocalTime.parse(userReserval.time()));
        if (reserval.isEmpty()) {
            throw new RuntimeException("Бронь не найдена!");
            //переделать!
        }
        var entity = reserval.get();
        entity.setState("false");
        reservalRepository.save(entity);
    }

    public void cancelReservalRest(UserEntity user, ReservalRest restReserval) throws ParseException {
        var userClient = userService.getByUsername(restReserval.username());
        var date = new SimpleDateFormat("dd.MM.yyyy").parse(restReserval.date());
        var reserval = reservalRepository
                .findByUserAndDateAndTimeStart(userClient,
                        date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        LocalTime.parse(restReserval.time()));
        if (reserval.isEmpty()) {
            throw new RuntimeException("Бронь не найдена!");
            //переделать!
        }
        var entity = reserval.get();
        entity.setState("false");
        reservalRepository.save(entity);
    }
}
