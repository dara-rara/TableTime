package com.example.TableTime.adapter.web.adminRest.dto.reserval;

public record ReservalRest(String username, String phone, String date, String time,
                           Integer table, Integer persons, String message) {
}
