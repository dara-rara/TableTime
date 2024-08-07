package com.example.TableTime.domain.restaurant;

import com.example.TableTime.domain.restaurant.photo.PhotoMenuEntity;
import com.example.TableTime.domain.restaurant.photo.PhotoPlanEntity;
import com.example.TableTime.domain.restaurant.photo.PhotoRestaurantEntity;
import com.example.TableTime.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;
import java.time.LocalTime;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
@Setter
@Getter
@Entity
@Table(name = "Restaurants")
@FieldNameConstants
@NoArgsConstructor
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_rest;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_town", nullable = false)
    private TownEntity town;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private UserEntity user;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_menu")
    private PhotoMenuEntity menu;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_plan")
    private PhotoPlanEntity plan;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_photo_rest")
    private PhotoRestaurantEntity photoRest;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "opening", nullable = false)
    private LocalTime opening;

    @Column(name = "ending", nullable = false)
    private LocalTime ending;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "description", nullable = false, length = 4000)
    private String description;

    @Column(name = "tables", nullable = false)
    private Integer tables;
}
