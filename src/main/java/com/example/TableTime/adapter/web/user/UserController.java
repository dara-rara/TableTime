package com.example.TableTime.adapter.web.user;

import com.example.TableTime.adapter.repository.UserRepository;
import com.example.TableTime.adapter.web.auth.dto.MessageResponse;
import com.example.TableTime.adapter.web.user.dto.*;
import com.example.TableTime.adapter.web.user.dto.reserval.ReservalAndTableForm;
import com.example.TableTime.adapter.web.user.dto.reserval.ReservalForm;
import com.example.TableTime.adapter.web.user.dto.reserval.ReservalRequest;
import com.example.TableTime.adapter.web.user.dto.reserval.UserReservalForm;
import com.example.TableTime.adapter.web.user.dto.review.ReviewForm;
import com.example.TableTime.config.jwt.JwtService;
import com.example.TableTime.domain.user.UserEntity;
import com.example.TableTime.service.ReservalService;
import com.example.TableTime.service.RestaurantService;
import com.example.TableTime.service.ReviewService;
import com.example.TableTime.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@PreAuthorize("hasAuthority('USER')")
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "/TableTime/", produces = APPLICATION_JSON_VALUE)
public class UserController {

    private final ReservalService reservalService;
    private final UserService userService;
    private final ReviewService reviewService;
    private final RestaurantService restaurantService;
    private final JwtService jwtService;

    @PostMapping("/freeTable/{id}")
    public ResponseEntity<?> listFreeTable(@PathVariable Long id, @RequestBody ReservalForm form) throws ParseException {
        if(restaurantService.findId(id).isEmpty())
            return ResponseEntity.badRequest().body(new MessageResponse("Ресторан не существует!"));
        return ResponseEntity.ok().body(reservalService.getFreeTablesUser(form, id));
    }

    @PostMapping("/reserval/{id}")
    public ResponseEntity<?> createReserval(@PathVariable Long id, @RequestBody ReservalAndTableForm form,
                               @AuthenticationPrincipal UserEntity user) throws ParseException {
        if(restaurantService.findId(id).isEmpty())
            return ResponseEntity.badRequest().body(new MessageResponse("Ресторан не существует!"));
        if (!reservalService.createReservalUser(form, id, user))
            return ResponseEntity.badRequest().body(new MessageResponse("Стол уже занят!"));
        return ResponseEntity.ok().body(new MessageResponse("Бронь создана!"));
    }

    @GetMapping("/user")
    public UserReservalForm getUser(@AuthenticationPrincipal UserEntity user) {
        return userService.getUserInfoReserval(user);
    }

    @PostMapping("/userUpdate")
    public ResponseEntity<?> updateUser(@AuthenticationPrincipal UserEntity user,
                                        @RequestBody AccountUpdate accountUpdate) {
        if (accountUpdate.getUsername().isEmpty() || accountUpdate.getEmail().isEmpty()
                || accountUpdate.getPhone().isEmpty() || accountUpdate.getRealname().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Поля не могут быть пустыми!"));
        }
        if (!user.getUsername().equals(accountUpdate.getUsername())
                && userService.nameExists(accountUpdate.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Такой логин уже существует!"));
        }
        if (!user.getEmail().equals(accountUpdate.getEmail())
                && userService.emailExists(accountUpdate.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Такой email уже существует!"));
        }
        userService.updateUser(user, accountUpdate);
        return ResponseEntity.ok().body(new UpdateToken(jwtService.generateToken(user)));
    }

    @DeleteMapping("/cancelReserval/{id}")
    public ResponseEntity<?> cancelReserval(@PathVariable Long id, @AuthenticationPrincipal UserEntity user) {
        if (reservalService.cancelReserval(id))
            return ResponseEntity.ok().body(new MessageResponse("Изменения сохранены!"));
        return ResponseEntity.badRequest().body(new MessageResponse("Данной брони не существует!"));
    }

    @PostMapping("/review")
    public ResponseEntity<?> createReview (@RequestBody ReviewForm reviewForm) {
        if (reviewService.createReview(reviewForm))
            return ResponseEntity.ok().body(new MessageResponse("Изменения сохранены!"));
        return ResponseEntity.badRequest().body(new MessageResponse("Данной брони не существует!"));
    }
}
