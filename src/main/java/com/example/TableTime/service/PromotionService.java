package com.example.TableTime.service;

import com.example.TableTime.adapter.repository.PromotionRepository;
import com.example.TableTime.adapter.repository.RestaurantRepository;
import com.example.TableTime.adapter.web.adminRest.dto.promotion.FormPromotion;
import com.example.TableTime.adapter.web.adminRest.dto.promotion.ListPromotion;
import com.example.TableTime.domain.restaurant.PromotionEntity;
import com.example.TableTime.domain.restaurant.RestaurantEntity;
import com.example.TableTime.domain.user.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Transactional
public class PromotionService {
    private final RestaurantRepository restaurantRepository;
    private final PromotionRepository promotionRepository;

    public void createPromotion(UserEntity user, FormPromotion form) {
        var optRest = restaurantRepository.findByUser(user);
        if (optRest.isEmpty())
            throw new UsernameNotFoundException("Пользователь не является администратором ресторана");

        var promotion = new PromotionEntity();
        promotion.setName(form.name());
        promotion.setDescription(form.text());
        promotion.setPhoto(form.photo());
        promotion.setRestaurant(optRest.get());
        promotionRepository.save(promotion);
    }

    public void updatePromotion(FormPromotion form, Long id) {
        var optProm = promotionRepository.findById(id);
        if (optProm.isEmpty())
            throw new UsernameNotFoundException("Акции не существует!");

        var promotion = optProm.get();
        promotion.setName(form.name());
        promotion.setDescription(form.text());
        promotion.setPhoto(form.photo());
        promotionRepository.save(promotion);
    }

    public LinkedList<ListPromotion> getPromotions(RestaurantEntity restaurantEntity) {
        var list = new LinkedList<ListPromotion>();
        var proms = promotionRepository.findByRestaurant(restaurantEntity);
        if (!proms.isEmpty()) {
            for (var prom : proms) {
                var form = new ListPromotion(prom.getId_prom(),
                        prom.getName(), prom.getDescription(), prom.getPhoto());
                list.add(form);
            }
        }
        return list;
    }

    public boolean checkPromotions (Long id) {
        return promotionRepository.existsById(id);
    }

    public boolean cancelPromotion(Long id) {
        var promotion = promotionRepository.findById(id);
        if (promotion.isEmpty()) return false;
        promotionRepository.delete(promotion.get());
        return true;
    }
}
