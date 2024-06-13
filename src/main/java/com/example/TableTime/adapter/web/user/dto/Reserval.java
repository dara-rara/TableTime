package com.example.TableTime.adapter.web.user.dto;

public record Reserval(Long id, String name, String phone, String date, String time,
                       Integer table, Integer persons, String message, String state) {
}
