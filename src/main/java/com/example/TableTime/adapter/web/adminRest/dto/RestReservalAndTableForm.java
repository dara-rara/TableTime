package com.example.TableTime.adapter.web.adminRest.dto;

public record RestReservalAndTableForm(String name,
                                       String phone,
                                       String date,
                                       String timeStart,
                                       String timeEnd,
                                       Integer persons,
                                       String message,
                                       Integer table) {
}
