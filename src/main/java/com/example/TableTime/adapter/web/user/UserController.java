package com.example.TableTime.adapter.web.user;

import com.example.TableTime.adapter.web.user.dto.*;
import com.example.TableTime.domain.user.UserEntity;
import com.example.TableTime.service.ReservalService;
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

    @PostMapping("/freeTable/{id}")
    public ReservalRequest listFreeTable(@PathVariable Long id, @RequestBody ReservalForm form) throws ParseException {
        return reservalService.getFreeTablesUser(form, id);
    }

    @PostMapping("/reserval/{id}")
    public void createReserval(@PathVariable Long id, @RequestBody ReservalAndTableForm form,
                                        @AuthenticationPrincipal UserEntity user) throws ParseException {
        reservalService.createReserval(form, id, user);
    }

    @GetMapping("/user")
    public UserReservalForm getUser(@AuthenticationPrincipal UserEntity user) {
        return userService.getUserInfoReserval(user);
    }

    @PostMapping("/userUpdate")
    public AccountUpdate updateUser(@AuthenticationPrincipal UserEntity user, @RequestBody AccountUpdate accountUpdate) {
        return userService.updateUser(user, accountUpdate);
    }

    @PostMapping("/cancelReserval")
    public UserReservalForm cancelReserval(@AuthenticationPrincipal UserEntity user, @RequestBody ReservalUser userReserval) throws ParseException {
        reservalService.cancelReservalUser(user, userReserval);
        return userService.getUserInfoReserval(user);
    }

}
