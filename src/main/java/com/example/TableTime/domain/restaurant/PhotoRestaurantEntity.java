package com.example.TableTime.domain.restaurant;

import com.example.TableTime.domain.user.UserEntity;
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
@Table(name = "PhotoRestaurants")
@FieldNameConstants
@NoArgsConstructor
public class PhotoRestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_photo;

    @Lob
    @Column(name = "photo1", nullable = true)
    private String photoOne;

    @Lob
    @Column(name = "photo2", nullable = true)
    private String photoTwo;

    @Lob
    @Column(name = "photo3", nullable = true)
    private String photoThree;
}
