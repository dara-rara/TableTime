package com.example.TableTime.adapter.web.adminApp;

import com.example.TableTime.adapter.web.adminApp.dto.RoleList;
import com.example.TableTime.adapter.web.adminApp.dto.UserRequest;
import com.example.TableTime.adapter.web.auth.dto.MessageResponse;
import com.example.TableTime.domain.user.Role;
import com.example.TableTime.service.AdminAppService;
import com.example.TableTime.service.RestaurantService;
import com.example.TableTime.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;



@RestController
@PreAuthorize("hasAuthority('ADMIN_APP')")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "/TableTime/adminApp/", produces = APPLICATION_JSON_VALUE)
public class AdminAppController {
    private final AdminAppService adminAppService;
    private final UserService userService;
    private final RestaurantService restaurantService;

    @PostMapping("/createRestaurant")
    public ResponseEntity<?> createRestaurant(@RequestBody UserRequest user) {
        if (!userService.emailExists(user.email()) || (userService.emailExists(user.email()) &&
                !userService.checkEmailAndUsername(user.username(), user.email()))) {
            return ResponseEntity.badRequest().body(new MessageResponse("Пользователя не существует!"));
        }
        var userRest = userService.getByEmail(user.email());
        if (restaurantService.checkAdminRest(userRest))
            return ResponseEntity.badRequest().body(new MessageResponse("Пользователь уже администратор ресторана!"));
        restaurantService.createRestaurant(adminAppService.changeRole(userRest, Role.ADMIN_REST));
        return ResponseEntity.ok(new MessageResponse("Ресторан создан!"));
    }

    @GetMapping("/getRoles")
    public RoleList getRoles() {
        return adminAppService.getRole();
    }

    @DeleteMapping("/deleteRestaurant")
    public ResponseEntity<?>  cancelReservalRest(@RequestBody UserRequest user) {
        if (!userService.emailExists(user.email()) || (userService.emailExists(user.email()) &&
                !userService.checkEmailAndUsername(user.username(), user.email()))) {
            return ResponseEntity.badRequest().body(new MessageResponse("Пользователя не существует!"));
        }

        if (adminAppService.deleteRestaurant(userService.getByEmail(user.email()))) {
            adminAppService.changeRole(userService.getByEmail(user.email()), Role.USER);
            return ResponseEntity.ok().body(new MessageResponse("Изменения сохранены!"));
        }
        return ResponseEntity.badRequest().body(new MessageResponse("Пользователь не администратор ресторана!"));
    }

}
