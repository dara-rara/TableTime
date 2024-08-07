package com.example.TableTime.adapter.web.user.dto.reserval;

import com.example.TableTime.domain.restaurant.reserval.State;

public record ReservalAndReview(Long id, String name, String phone, String date, String time,
                                Integer table, Integer persons, String message, State state, Integer grade, String review) {
}
