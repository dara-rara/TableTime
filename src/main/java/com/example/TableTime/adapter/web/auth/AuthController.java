package com.example.TableTime.adapter.web.auth;

import com.example.TableTime.adapter.repository.UserRepository;
import com.example.TableTime.adapter.web.adminRest.dto.RestaurantData;
import com.example.TableTime.adapter.web.auth.dto.*;
import com.example.TableTime.adapter.web.auth.dto.RestaurantList;
import com.example.TableTime.domain.user.UserEntity;
import com.example.TableTime.service.AuthService;
import com.example.TableTime.service.RestaurantService;
import com.example.TableTime.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "/TableTime/", produces = APPLICATION_JSON_VALUE)
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final RestaurantService restaurantService;

    @GetMapping("/restaurants")
    public List<RestaurantList> getRestList() {
        return restaurantService.listRest();
    }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<?> getRest(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(restaurantService.getFormRestaurant(restaurantService.findId(id).get()));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Ресторан не существует!"));
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> signUp(@RequestBody RegistrationRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Такой логин уже существует!"));
        }
        if (userRepository.existsByEmail(request.email())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Такой email уже существует!"));
        }
        return ResponseEntity.ok(authService.signUp(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@RequestBody LoginUser request) {
        try {
            if (!userRepository.existsByUsername(request.getUsername())) {
                return ResponseEntity.badRequest()
                        .body(new MessageResponse("Такого логина не существует!"));
            }
            return ResponseEntity.ok(authService.signIn(request));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Неправильно набран пароль!"));
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(new StatusResponse(user.getRole()));
    }
}
