package com.example.TableTime.adapter.web.user.dto;

import java.util.List;

public record ReservalRequest (String date,
                               String timeStart,
                               String timeEnd,
                               Integer persons,
                               String message,
                               List<Integer> tables) {
}