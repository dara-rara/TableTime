package com.example.TableTime.adapter.web.adminRest.dto;

import java.util.List;

public record RestaurantData(String name,
                             String town,
                             String address,
                             String opening,
                             String ending,
                             String phone,
                             String description,
                             String tables,
                             List<String> photosRest,
                             String plan,
                             String menu){
}