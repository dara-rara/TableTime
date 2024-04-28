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
@Table(name = "Towns")
@FieldNameConstants
@NoArgsConstructor
public class TownEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_town;

    @Column(name = "name", nullable = false)
    private String name;
}
