package com.example.TableTime.adapter.web.adminRest.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class AccountRestUpdate {
    private String username;
    private String email;
    private String phone;
    private String message;
}