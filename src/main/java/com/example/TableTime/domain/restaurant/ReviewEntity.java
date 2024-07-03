package com.example.TableTime.domain.restaurant;

import com.example.TableTime.domain.restaurant.reserval.ReservalEntity;
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
@Table(name = "Reviews")
@FieldNameConstants
@NoArgsConstructor
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_rev;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rest")
    private RestaurantEntity restaurant;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_res")
    private ReservalEntity reserval;

    @Column(name = "text", nullable = true)
    private String text;

    @Column(name = "grade", nullable = false)
    private Integer grade;
}
