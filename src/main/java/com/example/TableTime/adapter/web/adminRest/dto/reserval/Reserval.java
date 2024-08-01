package com.example.TableTime.adapter.web.adminRest.dto.reserval;

import com.example.TableTime.domain.restaurant.reserval.State;

public record Reserval(Long id, String name, String phone, String date, String time,
                       Integer table, Integer persons, String message, State state) {
}
