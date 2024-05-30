package com.example.TableTime.adapter.web.user.dto;

import java.util.List;

public record UserForm(String login, String name, String email,
                       String phone, List<UserReserval> reservals) {
}
