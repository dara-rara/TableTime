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
@Table(name = "PhotoMenu")
@FieldNameConstants
@NoArgsConstructor
public class PhotoMenuEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_photo;

    @Lob
    @Column(name = "photo", nullable = true)
    private String photo;

}
