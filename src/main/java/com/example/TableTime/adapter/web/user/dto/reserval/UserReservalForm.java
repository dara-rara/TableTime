package com.example.TableTime.adapter.web.user.dto.reserval;

import com.example.TableTime.adapter.web.user.dto.reserval.Reserval;

import java.util.LinkedList;

public record UserReservalForm(String username, String realname, String email,
                               String phone, LinkedList<Reserval> reservals) {
}
