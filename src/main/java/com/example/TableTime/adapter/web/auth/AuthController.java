package com.example.TableTime.adapter.web.auth;

import com.example.TableTime.adapter.repository.UserRepository;
import com.example.TableTime.adapter.web.adminRest.dto.RestaurantData;
import com.example.TableTime.adapter.web.auth.dto.*;
import com.example.TableTime.adapter.web.auth.dto.RestaurantList;
import com.example.TableTime.domain.user.UserEntity;
import com.example.TableTime.service.AuthService;
import com.example.TableTime.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

//@CrossOrigin(origins = "http://10.76.45.18:5173")
@Validated
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
    public RestaurantData getRest(@PathVariable Long id) {
        return restaurantService.getFormRestaurant(restaurantService.findId(id));
    }

    @PostMapping("/users")
    public ResponseEntity<?> signUp(@RequestBody RegistrationRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(request.email())) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        return ResponseEntity.ok(authService.signUp(request));
    }

    @PostMapping("/login")
    public ResponseEntity<?> signIn(@Valid @RequestBody LoginUser request) {
        return ResponseEntity.ok(authService.signIn(request));
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus(@AuthenticationPrincipal UserEntity user) {
        return ResponseEntity.ok(new StatusResponse(user.getRole()));
    }
}
