package com.example.TableTime.adapter.web.auth.dto;

import com.example.TableTime.domain.user.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class JwtAuthenticationResponse {
    private final String token;
    private final Role role;
}