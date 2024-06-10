package com.example.TableTime.adapter.web.user.dto;

public record Reserval(String restName, String phone, String date, String time,
                       Integer table, Integer persons, String message, String state) {
}
