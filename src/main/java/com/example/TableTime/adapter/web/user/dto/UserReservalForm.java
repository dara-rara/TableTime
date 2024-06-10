package com.example.TableTime.adapter.web.user.dto;

import java.util.LinkedList;

public record UserReservalForm(String login, String name, String email,
                               String phone, LinkedList<Reserval> reservals) {
}
