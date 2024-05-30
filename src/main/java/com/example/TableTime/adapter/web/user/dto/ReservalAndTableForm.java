package com.example.TableTime.adapter.web.user.dto;

public record ReservalAndTableForm(String date,
                                   String timeStart,
                                   String timeEnd,
                                   Integer persons,
                                   String message,
                                   Integer table) {
}
