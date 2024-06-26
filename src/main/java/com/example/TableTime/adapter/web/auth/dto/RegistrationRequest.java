package com.example.TableTime.adapter.web.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(@NotBlank String username,
                                  @NotBlank String realname,
                                  @Email
                                  @NotBlank String email,
                                  @NotBlank String phone,
                                  @NotBlank String password){
}
