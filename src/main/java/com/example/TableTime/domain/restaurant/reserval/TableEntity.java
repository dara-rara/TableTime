package com.example.TableTime.domain.restaurant.reserval;

import com.example.TableTime.domain.restaurant.RestaurantEntity;
import com.example.TableTime.domain.restaurant.reserval.State;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
@Setter
@Getter
@Entity
@Table(name = "Tables")
@FieldNameConstants
@NoArgsConstructor
public class TableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_tab;

    @Column(name = "number", nullable = false)
    private Integer number;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rest", nullable = false)
    private RestaurantEntity restaurant;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;
}
