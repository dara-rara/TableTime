package com.example.TableTime.adapter.web.user.dto.reserval;

import java.util.LinkedList;

public record UserReservalForm(String username, String realname, String email,
                               String phone, LinkedList<ReservalAndReview> reservals) {
}
