package com.example.TableTime.adapter.web.user.dto;

public record ReservalForm(String date,
                           String timeStart,
                           String timeEnd,
                           Integer persons,
                           String message) {
}
