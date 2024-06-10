package com.example.TableTime.adapter.web.adminRest.dto;

import com.example.TableTime.adapter.web.user.dto.Reserval;

import java.util.LinkedList;

public record RestReservalForm(String login, String email,
                               String phone, LinkedList<Reserval> reservals) {
}
