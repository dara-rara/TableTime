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

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private UserEntity user;

    @Lob
    @Column(name = "photo1", nullable = true)
    private byte[] photoOne;

    @Column(name = "contentType1", nullable = true)
    private String contentTypeOne;

    @Lob
    @Column(name = "photo2", nullable = true)
    private byte[] photoTwo;

    @Column(name = "contentType2", nullable = true)
    private String contentTypeTwo;

    @Lob
    @Column(name = "photo3", nullable = true)
    private byte[] photoThree;

    @Column(name = "contentType3", nullable = true)
    private String contentTypeThree;

}
