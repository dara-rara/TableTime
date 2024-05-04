package com.example.TableTime.adapter.web.restaurant.dto;

import jakarta.validation.constraints.NotBlank;

public record RestaurantForm(@NotBlank
                             String name,
                             @NotBlank
                             String town,
                             @NotBlank
                             String address,
                             @NotBlank
                             String opening,
                             @NotBlank
                             String ending,
                             @NotBlank
                             String phone,
                             @NotBlank
                             String description,
                             @NotBlank
                             String tables){
}
