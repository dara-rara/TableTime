package com.example.TableTime.adapter.web.adminRest;

import com.example.TableTime.adapter.web.adminRest.dto.RestaurantData;
import com.example.TableTime.adapter.web.adminRest.dto.RestaurantInfo;
import com.example.TableTime.domain.user.UserEntity;
import com.example.TableTime.service.AdminRestService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@PreAuthorize("hasAuthority('ADMIN_REST')")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "/TableTime/adminRest/", produces = APPLICATION_JSON_VALUE)
public class AdminRestController {
    private final AdminRestService adminRestService;

    @GetMapping("/restaurant")
    public RestaurantData getRestaurant(@AuthenticationPrincipal UserEntity user) {
        return adminRestService.getFormRestaurant(adminRestService.getRestaurant(user),
                adminRestService.getPhotoRest(user), adminRestService.getPlan(user), adminRestService.getMenu(user));
    }

    @PostMapping("/updateRestaurant")
    public void updateRestaurant(@AuthenticationPrincipal UserEntity user,
                                 @RequestBody RestaurantInfo form) {
        adminRestService.updateRestaurant(user, form);
    }

    @PostMapping("/updateMenu")
    public ResponseEntity updateMenu(@AuthenticationPrincipal UserEntity user,
                                     @RequestParam("menu") MultipartFile menu) {
        try {
            adminRestService.updateMenu(user, menu);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/updatePlan")
    public ResponseEntity updatePlan(@AuthenticationPrincipal UserEntity user,
                                     @RequestParam("plan") MultipartFile plan) {
        try {
            adminRestService.updatePlan(user, plan);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/updatePhotoRestaurant")
    public ResponseEntity updatePhotoRest(@AuthenticationPrincipal UserEntity user,
                                          @RequestParam("photo1") MultipartFile photo1,
                                          @RequestParam("photo2") MultipartFile photo2,
                                          @RequestParam("photo3") MultipartFile photo3) {
        try {
            adminRestService.updatePhotoRest(user, photo1, photo2, photo3);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
//    @PostMapping("/updateMenu")
//    public ResponseEntity updateMenu(@AuthenticationPrincipal UserEntity user,
//                                     @RequestParam("menu") MultipartFile menu) {
//        try {
//            adminRestService.updateMenu(user, menu);
//            return ResponseEntity.status(HttpStatus.OK).build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @PostMapping("/updatePlan")
//    public ResponseEntity updatePlan(@AuthenticationPrincipal UserEntity user,
//                                     @RequestParam("plan") MultipartFile plan) {
//        try {
//            adminRestService.updatePlan(user, plan);
//            return ResponseEntity.status(HttpStatus.OK).build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    @PostMapping("/updatePhotoRestaurant")
//    public ResponseEntity updatePhotoRest(@AuthenticationPrincipal UserEntity user,
//                                     @RequestParam("photo1") MultipartFile photo1,
//                                     @RequestParam("photo2") MultipartFile photo2,
//                                     @RequestParam("photo3") MultipartFile photo3) {
//        try {
//            adminRestService.updatePhotoRest(user, photo1, photo2, photo3);
//            return ResponseEntity.status(HttpStatus.OK).build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//    @PostMapping("/updateMenu")
//    public ResponseEntity<String> updateMenu(@RequestParam("menu") MultipartFile menu,
//                                         @AuthenticationPrincipal UserEntity user) {
//        try {
//            restaurantService.saveMenu(user, menu);
//
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(String.format("File uploaded successfully: %s", menu.getOriginalFilename()));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(String.format("Could not upload the file: %s!", menu.getOriginalFilename()));
//        }
//    }
//
//    @GetMapping("/getMenu")
//    public ResponseEntity<byte[]> getMenu(@AuthenticationPrincipal UserEntity user) {
//        var menu = restaurantService.getMenu(user);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "menu" + "\"")
//                .contentType(MediaType.valueOf(menu.getContentType()))
//                .body(menu.getPhoto());
//    }
}
