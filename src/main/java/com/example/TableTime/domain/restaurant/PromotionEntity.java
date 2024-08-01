package com.example.TableTime.domain.restaurant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
@Setter
@Getter
@Entity
@Table(name = "Promotion")
@FieldNameConstants
@NoArgsConstructor
public class PromotionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_prom;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rest")
    private RestaurantEntity restaurant;

    @Column(name = "description", nullable = false, length = 2000)
    private String description;

    @Lob
    @Column(name = "photo", nullable = true)
    private String photo;
}
