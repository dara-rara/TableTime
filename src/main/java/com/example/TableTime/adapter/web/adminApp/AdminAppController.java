package com.example.TableTime.adapter.web.adminApp;

import com.example.TableTime.adapter.web.adminApp.dto.RoleList;
import com.example.TableTime.adapter.web.adminApp.dto.UserRequest;
import com.example.TableTime.domain.user.Role;
import com.example.TableTime.service.AdminAppService;
import com.example.TableTime.service.AdminRestService;
import com.example.TableTime.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;



@RestController
@PreAuthorize("hasAuthority('ADMIN_APP')")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "/TableTime/adminApp/", produces = APPLICATION_JSON_VALUE)
public class AdminAppController {
    private final AdminAppService adminAppService;
    private final UserService userService;
    private final AdminRestService restaurantService;

    @PostMapping("/createRestaurant")
    public ResponseEntity createRestaurant(@RequestBody UserRequest user) {
        restaurantService.createRestaurant(adminAppService.changeRole(userService
                                .getUser(user.email()), Role.ADMIN_REST));

        return ResponseEntity.status(HttpStatus.OK).build();
    }

//    @PostMapping("/createRestaurant")
//    public ResponseEntity createRestaurant(@RequestParam("photo") MultipartFile photo,
//                                           @RequestParam("login") String login,
//                                           @RequestParam("email") String email) {
//        try {
//            restaurantService.createRestaurant(adminAppService
//                    .changeRole(userService
//                            .getUser(email), Role.ADMIN_REST),
//                    photo);
//
//            return ResponseEntity.status(HttpStatus.OK).build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    @GetMapping("/getRoles")
    public RoleList getRoles() {
        return adminAppService.getRole();
    }

}
