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
    @Column(name = "photo1", nullable = false)
    private byte[] photoOne;

    @Column(name = "contentType1", nullable = false)
    private String contentTypeOne;

    @Lob
    @Column(name = "photo2", nullable = false)
    private byte[] photoTwo;

    @Column(name = "contentType2", nullable = false)
    private String contentTypeTwo;

    @Lob
    @Column(name = "photo3", nullable = false)
    private byte[] photoThree;

    @Column(name = "contentType3", nullable = false)
    private String contentTypeThree;

}
