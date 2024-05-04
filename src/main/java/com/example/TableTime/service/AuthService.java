package com.example.TableTime.service;

import com.example.TableTime.adapter.web.auth.dto.LoginUser;
import com.example.TableTime.adapter.web.auth.dto.RegistrationRequest;
import com.example.TableTime.adapter.web.auth.dto.JwtAuthenticationResponse;
import com.example.TableTime.config.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    //Регистрация пользователя
    public JwtAuthenticationResponse signUp(RegistrationRequest registrationRequest) {

        var user = userService.saveUser(registrationRequest);
        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt, user.getRole());
    }

    //Аутентификация пользователя
    public JwtAuthenticationResponse signIn(LoginUser request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService.loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt, user.getRole());
    }
}
