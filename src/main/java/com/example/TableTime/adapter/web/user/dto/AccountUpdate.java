package com.example.TableTime.adapter.web.user.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class AccountUpdate {
    private String username;
    private String realname;
    private String email;
    private String phone;
}
