package com.example.TableTime.adapter.web.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantList {
    private Long id;
    private String name;
    private String town;
    private String address;
    private String phone;
    private String photo;
    private Integer grade;
}
