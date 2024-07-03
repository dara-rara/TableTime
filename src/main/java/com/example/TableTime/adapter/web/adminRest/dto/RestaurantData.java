package com.example.TableTime.adapter.web.adminRest.dto;

import java.util.LinkedList;
import java.util.List;

public record RestaurantData(Long id,
                             String name,
                             String town,
                             String address,
                             String opening,
                             String ending,
                             String phone,
                             String description,
                             String tables,
                             List<String> photosRest,
                             String plan,
                             String menu,
                             LinkedList<ReviewData> reviewData,
                             Integer avgRating){
}