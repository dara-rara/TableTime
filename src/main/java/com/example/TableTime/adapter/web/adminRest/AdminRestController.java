package com.example.TableTime.adapter.web.adminRest;

import com.example.TableTime.adapter.web.adminRest.dto.*;
import com.example.TableTime.domain.user.UserEntity;
import com.example.TableTime.service.AdminRestService;
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
public class AdminRestController {
    private final AdminRestService adminRestService;
    private final RestaurantService restaurantService;

    @GetMapping("/restaurant")
    public RestaurantData getRestaurant(@AuthenticationPrincipal UserEntity user) {
        return restaurantService.getFormRestaurant(adminRestService.getRestaurant(user));
    }

    @PostMapping("/updateRestaurant")
    public RestaurantInfo updateRestaurant(@AuthenticationPrincipal UserEntity user,
                                           @RequestBody RestaurantInfo form) {
        adminRestService.updateRestaurant(user, form);
        return form;
    }

    @PostMapping("/updateMenu")
    public void updateMenu(@AuthenticationPrincipal UserEntity user, @RequestBody PhotoMenuOrPlan photo) {
        adminRestService.updateMenu(user, photo.photo());
    }

    @PostMapping("/updateTable")
    public void updateTable(@AuthenticationPrincipal UserEntity user, @RequestBody TablesForm form) {
        adminRestService.updateTables(user, form);
    }

    @PostMapping("/updatePlan")
    public void updatePlan(@AuthenticationPrincipal UserEntity user, @RequestBody PhotoMenuOrPlan photo) {
        adminRestService.updatePlan(user, photo.photo());
    }

    @PostMapping("/updatePhotoRestaurant")
    public void updatePhotoRest(@AuthenticationPrincipal UserEntity user, @RequestBody PhotoRest photo) {
        adminRestService.updatePhotoRest(user, photo.photo1(), photo.photo2(), photo.photo3());
    }
}
