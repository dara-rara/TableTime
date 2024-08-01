package com.example.TableTime.adapter.web.adminRest.dto.reserval;

import java.util.LinkedList;

public record RestReservalForm(String username, String email,
                               String phone, LinkedList<Reserval> reservals) {
}
