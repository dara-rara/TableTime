package com.example.TableTime.service;

import com.example.TableTime.adapter.repository.ReservalRepository;
import com.example.TableTime.adapter.repository.RestaurantRepository;
import com.example.TableTime.adapter.repository.TableRepository;
import com.example.TableTime.adapter.web.user.dto.ReservalAndTableForm;
import com.example.TableTime.adapter.web.user.dto.ReservalForm;
import com.example.TableTime.adapter.web.user.dto.ReservalRequest;
import com.example.TableTime.domain.restaurant.ReservalEntity;
import com.example.TableTime.domain.user.UserEntity;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ReservalService {
    private final TableRepository tableRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReservalRepository reservalRepository;

    public ReservalRequest getFreeTables(ReservalForm form, Long id) throws ParseException {
        var allTables = tableRepository.findByAllTable(id);

        var resTables = tableRepository.findByReservalTable(id,
                LocalDate.parse(form.date(), DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                new SimpleDateFormat("HH:mm").parse(form.timeStart()),
                new SimpleDateFormat("HH:mm").parse(form.timeEnd()));
        allTables.removeIf(s -> {
            if (resTables.contains(s)) {
                resTables.remove(s);
                return true;
            }
            return false;
        });
        return new ReservalRequest(form.date(), form.timeStart(), form.timeEnd(), form.persons(), form.message(), allTables);
    }

    public void createReserval(ReservalAndTableForm form, Long id, UserEntity user) throws ParseException {
        var table = tableRepository.findByRestaurantAndNumber(
                restaurantRepository.getReferenceById(id), form.table());
        var reserval = new ReservalEntity();
        reserval.setUser(user);
        reserval.setTable(table);
        reserval.setPersons(form.persons());
        reserval.setTimeStart(new SimpleDateFormat("HH:mm").parse(form.timeStart()));
        reserval.setTimeEnd(new SimpleDateFormat("HH:mm").parse(form.timeEnd()));
        reserval.setDate(LocalDate.parse(form.date(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        reserval.setState(true);
        reserval.setMessage(form.message());
        reservalRepository.save(reserval);
    }
}
