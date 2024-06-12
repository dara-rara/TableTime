package com.example.TableTime.adapter.web.user.dto;

import java.util.LinkedList;

public record UserReservalForm(String username, String realname, String email,
                               String phone, LinkedList<Reserval> reservals) {
}
