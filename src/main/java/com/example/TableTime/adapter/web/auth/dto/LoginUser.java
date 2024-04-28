package com.example.TableTime.adapter.web.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import static lombok.AccessLevel.PRIVATE;

@Setter
@Getter
@FieldNameConstants
@NoArgsConstructor
@FieldDefaults(level = PRIVATE)
public class LoginUser {
    @NotBlank(message = "Имя пользователя не должно быть пустым")
    private String username;
    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

}
