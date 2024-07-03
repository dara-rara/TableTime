package com.example.TableTime.adapter.web.user;

import com.example.TableTime.adapter.web.user.dto.*;
import com.example.TableTime.adapter.web.user.dto.reserval.ReservalAndTableForm;
import com.example.TableTime.adapter.web.user.dto.reserval.ReservalForm;
import com.example.TableTime.adapter.web.user.dto.reserval.ReservalRequest;
import com.example.TableTime.adapter.web.user.dto.reserval.UserReservalForm;
import com.example.TableTime.adapter.web.user.dto.review.ReviewForm;
import com.example.TableTime.domain.user.UserEntity;
import com.example.TableTime.service.ReservalService;
import com.example.TableTime.service.ReviewService;
import com.example.TableTime.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/freeTable/{id}")
    public ReservalRequest listFreeTable(@PathVariable Long id, @RequestBody ReservalForm form) throws ParseException {
        return reservalService.getFreeTablesUser(form, id);
    }

    @PostMapping("/reserval/{id}")
    public void createReserval(@PathVariable Long id, @RequestBody ReservalAndTableForm form,
                               @AuthenticationPrincipal UserEntity user) throws ParseException {
        reservalService.createReservalUser(form, id, user);
    }

    @GetMapping("/user")
    public UserReservalForm getUser(@AuthenticationPrincipal UserEntity user) {
        return userService.getUserInfoReserval(user);
    }

    @PostMapping("/userUpdate")
    public void updateUser(@AuthenticationPrincipal UserEntity user, @RequestBody AccountUpdate accountUpdate) {
        userService.updateUser(user, accountUpdate);
    }

    @DeleteMapping("/cancelReserval/{id}")
    public void cancelReserval(@PathVariable Long id, @AuthenticationPrincipal UserEntity user) throws ParseException {
        reservalService.cancelReserval(id);
    }

    @PostMapping("/review")
    public void createReview (@RequestBody ReviewForm reviewForm) {
        reviewService.createReview(reviewForm);
    }
}
