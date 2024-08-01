package com.example.TableTime.adapter.web.adminRest;

import com.example.TableTime.adapter.web.adminRest.dto.*;
import com.example.TableTime.adapter.web.adminRest.dto.promotion.FormPromotion;
import com.example.TableTime.adapter.web.adminRest.dto.reserval.*;
import com.example.TableTime.adapter.web.auth.dto.MessageResponse;
import com.example.TableTime.adapter.web.user.dto.UpdateToken;
import com.example.TableTime.config.jwt.JwtService;
import com.example.TableTime.domain.user.UserEntity;
import com.example.TableTime.service.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@PreAuthorize("hasAuthority('ADMIN_REST')")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping(value = "/TableTime/adminRest/", produces = APPLICATION_JSON_VALUE)
public class AdminRestController {
    private final AdminRestService adminRestService;
    private final RestaurantService restaurantService;
    private final ReservalService reservalService;
    private final UserService userService;
    private final PromotionService promotionService;
    private final JwtService jwtService;

    @GetMapping("/restaurant")
    public ResponseEntity<?> getRestaurant(@AuthenticationPrincipal UserEntity user) {
        if (!restaurantService.existUserRest(user)) return ResponseEntity.badRequest()
                .body(new MessageResponse("Ресторан не существует!"));
        return ResponseEntity.ok(restaurantService.getFormRestaurant( restaurantService.getRestaurant(user)));
    }

    @PostMapping("/updateRestaurant")
    public ResponseEntity<?>  updateRestaurant(@AuthenticationPrincipal UserEntity user,
                                           @RequestBody RestaurantInfo form) {
        if (!restaurantService.existUserRest(user)) return ResponseEntity.badRequest()
                .body(new MessageResponse("Ресторан не существует!"));
        adminRestService.updateRestaurant(user, form);
        return ResponseEntity.ok(form);
    }

    @PostMapping("/updateMenu")
    public ResponseEntity<?>  updateMenu(@AuthenticationPrincipal UserEntity user, @RequestBody PhotoMenuOrPlan photo) {
        if (!restaurantService.existUserRest(user)) return ResponseEntity.badRequest()
                .body(new MessageResponse("Ресторан не существует!"));
        adminRestService.updateMenu(user, photo.photo());
        return ResponseEntity.ok(new MessageResponse("Данные изменены!"));
    }

    @PostMapping("/updateTable")
    public ResponseEntity<?>  updateTable(@AuthenticationPrincipal UserEntity user, @RequestBody TablesForm form) {
        if (!restaurantService.existUserRest(user)) return ResponseEntity.badRequest()
                .body(new MessageResponse("Ресторан не существует!"));
        adminRestService.updateTables(user, form);
        return ResponseEntity.ok(new MessageResponse("Данные изменены!"));
    }

    @PostMapping("/updatePlan")
    public ResponseEntity<?>  updatePlan(@AuthenticationPrincipal UserEntity user, @RequestBody PhotoMenuOrPlan photo) {
        if (!restaurantService.existUserRest(user)) return ResponseEntity.badRequest()
                .body(new MessageResponse("Ресторан не существует!"));
        adminRestService.updatePlan(user, photo.photo());
        return ResponseEntity.ok(new MessageResponse("Данные изменены!"));
    }

    @PostMapping("/updatePhotoRestaurant")
    public ResponseEntity<?>  updatePhotoRest(@AuthenticationPrincipal UserEntity user, @RequestBody PhotoRest photo) {
        if (!restaurantService.existUserRest(user)) return ResponseEntity.badRequest()
                .body(new MessageResponse("Ресторан не существует!"));
        adminRestService.updatePhotoRest(user, photo.photo1(), photo.photo2(), photo.photo3());
        return ResponseEntity.ok(new MessageResponse("Данные изменены!"));
    }

    @PostMapping("/userUpdate")
    public ResponseEntity<?>  updateUser(@AuthenticationPrincipal UserEntity user, @RequestBody AccountRestUpdate accountRequest) {
        if (accountRequest.getUsername().isEmpty() || accountRequest.getEmail().isEmpty()
                || accountRequest.getPhone().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Поля не могут быть пустыми!"));
        }
        if (!user.getUsername().equals(accountRequest.getUsername())
                && userService.nameExists(accountRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Такой логин уже существует!"));
        }
        if (!user.getEmail().equals(accountRequest.getEmail())
                && userService.emailExists(accountRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Такой email уже существует!"));
        }
        adminRestService.updateUser(user, accountRequest);
        return ResponseEntity.ok(new UpdateToken(jwtService.generateToken(user)));
    }

    @PostMapping("/getReservals")
    public ResponseEntity<?>  getReservals(@AuthenticationPrincipal UserEntity user, @RequestBody RestDateRequest request) throws ParseException {
        if (!restaurantService.existUserRest(user)) return ResponseEntity.badRequest()
                .body(new MessageResponse("Ресторан не существует!"));
        return  ResponseEntity.ok(adminRestService.getRestInfoReserval(user, request));
    }

    @GetMapping("/getPromotions")
    public ResponseEntity<?>  getPromotions(@AuthenticationPrincipal UserEntity user) {
        if (!restaurantService.existUserRest(user)) return ResponseEntity.badRequest()
                .body(new MessageResponse("Ресторан не существует!"));
        return  ResponseEntity.ok(promotionService.getPromotions(restaurantService.getRestaurant(user)));
    }

    @PostMapping("/promotion")
    public ResponseEntity<?> createPromotion(@AuthenticationPrincipal UserEntity user, @RequestBody FormPromotion formPromotion) {
        if (!restaurantService.existUserRest(user)) return ResponseEntity.badRequest()
                .body(new MessageResponse("Ресторан не существует!"));
        promotionService.createPromotion(user, formPromotion);
        return  ResponseEntity.ok(new MessageResponse("Акция создана!"));
    }

    @GetMapping("/promotion/{id}")
    public ResponseEntity<?> getPromotion(@AuthenticationPrincipal UserEntity user, @PathVariable Long id) {
        if (!restaurantService.existUserRest(user)) return ResponseEntity.badRequest()
                .body(new MessageResponse("Ресторан не существует!"));
        if (!promotionService.checkPromotions(id)) return ResponseEntity.badRequest()
                .body(new MessageResponse("Акция не существует!"));
        return  ResponseEntity.ok(promotionService.getPromotion(id));
    }

    @PostMapping("/updatePromotion/{id}")
    public ResponseEntity<?> updatePromotion(@PathVariable Long id, @RequestBody FormPromotion formPromotion) {
        if (!promotionService.checkPromotions(id)) return ResponseEntity.badRequest()
                .body(new MessageResponse("Акция не существует!"));
        promotionService.updatePromotion(formPromotion, id);
        return  ResponseEntity.ok(new MessageResponse("Изменения сохранены!"));
    }

    @DeleteMapping("/cancelPromotion/{id}")
    public ResponseEntity<?> cancelPromotion(@PathVariable Long id) {
        if (promotionService.cancelPromotion(id))
            return ResponseEntity.ok().body(new MessageResponse("Изменения сохранены!"));
        return ResponseEntity.badRequest().body(new MessageResponse("Данной брони не существует!"));
    }

    @DeleteMapping("/cancelReserval/{id}")
    public ResponseEntity<?>  cancelReservalRest(@PathVariable Long id) {
        if (reservalService.cancelReserval(id))
            return ResponseEntity.ok().body(new MessageResponse("Изменения сохранены!"));
        return ResponseEntity.badRequest().body(new MessageResponse("Данной брони не существует!"));
    }

    @PostMapping("/freeTable")
    public ResponseEntity<?> listFreeTable(@AuthenticationPrincipal UserEntity user,
                                           @RequestBody ReservalRestFreeTable form) throws ParseException {
        if (!restaurantService.existUserRest(user)) return ResponseEntity.badRequest()
                .body(new MessageResponse("Ресторан не существует!"));
        return ResponseEntity.ok(reservalService.getFreeTablesRest(form,
                restaurantService.getRestaurant(user).getId_rest()));
    }

    @PostMapping("/reserval")
    public ResponseEntity<?> createReserval(@RequestBody RestReservalAndTableForm form,
                               @AuthenticationPrincipal UserEntity user) throws ParseException {
        if (!restaurantService.existUserRest(user)) return ResponseEntity.badRequest()
                .body(new MessageResponse("Ресторан не существует!"));

        if (!reservalService.createReservalRest(form,  restaurantService.getRestaurant(user).getId_rest(),
                reservalService.createUnregistUser(form.name(), form.phone())))
            return ResponseEntity.badRequest().body(new MessageResponse("Стол уже занят!"));
        return ResponseEntity.ok(new MessageResponse("Бронь создана!"));
    }
}
