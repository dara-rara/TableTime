package com.example.TableTime.adapter.web.user;

import com.example.TableTime.adapter.web.adminRest.dto.RestaurantData;
import com.example.TableTime.adapter.web.user.dto.RestaurantList;
import com.example.TableTime.service.RestaurantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@PreAuthorize("hasAuthority('USER')")
@RestController
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "/TableTime/", produces = APPLICATION_JSON_VALUE)
public class UserController {

}
