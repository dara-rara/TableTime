package com.example.TableTime.adapter.web.user;

import com.example.TableTime.adapter.web.auth.dto.RestaurantList;
import com.example.TableTime.adapter.web.user.dto.ReservalAndTableForm;
import com.example.TableTime.adapter.web.user.dto.ReservalForm;
import com.example.TableTime.domain.user.UserEntity;
import com.example.TableTime.service.ReservalService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@PreAuthorize("hasAuthority('USER')")
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "/TableTime/", produces = APPLICATION_JSON_VALUE)
public class UserController {

    private final ReservalService reservalService;

    @PostMapping("/freeTable/{id}")
    public List<Integer> listFreeTable(@PathVariable Long id, @RequestBody ReservalForm form) throws ParseException {
        return reservalService.getFreeTables(form, id);
    }

    @PostMapping("/reserval/{id}")
    public void createReserval(@PathVariable Long id, @RequestBody ReservalAndTableForm form,
                                        @AuthenticationPrincipal UserEntity user) throws ParseException {
        reservalService.createReserval(form, id, user);
    }

}
