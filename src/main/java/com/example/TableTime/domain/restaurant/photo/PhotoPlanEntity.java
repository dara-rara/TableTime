package com.example.TableTime.domain.restaurant.photo;

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
@Table(name = "PhotoPlan")
@FieldNameConstants
@NoArgsConstructor
public class PhotoPlanEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_photo;

    @Lob
    @Column(name = "photo", nullable = true)
    private String photo;
}
