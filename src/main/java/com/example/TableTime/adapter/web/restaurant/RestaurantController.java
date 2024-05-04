package com.example.TableTime.adapter.web.restaurant;

import com.example.TableTime.adapter.web.restaurant.dto.RestaurantForm;
import com.example.TableTime.domain.user.UserEntity;
import com.example.TableTime.service.RestaurantService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@PreAuthorize("hasAuthority('ADMIN_REST')")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "/TableTime/adminRest/", produces = APPLICATION_JSON_VALUE)
public class RestaurantController {
    private final RestaurantService restaurantService;

    @GetMapping("/restaurant")
    public RestaurantForm getRestaurant(@AuthenticationPrincipal UserEntity user) {
        return restaurantService.getFormRestaurant(restaurantService.getRestaurant(user));
    }

    @PostMapping("/updateRestaurant")
    public void updateRestaurant(@AuthenticationPrincipal UserEntity user, @RequestBody RestaurantForm form) {
        restaurantService.updateRestaurant(user, form);
    }

}
