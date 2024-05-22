package com.example.TableTime.domain.restaurant;

import com.example.TableTime.domain.user.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.FieldNameConstants;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import static lombok.AccessLevel.PRIVATE;

@FieldDefaults(level = PRIVATE)
@Setter
@Getter
@Entity
@Table(name = "Reservals")
@FieldNameConstants
@NoArgsConstructor
public class ReservalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_res;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_tab")
    private TableEntity table;

    @Column(name = "persons", nullable = false)
    private Integer persons;

    @Column(name = "time_start", nullable = false)
    private Date timeStart;

    @Column(name = "time_end", nullable = false)
    private Date timeEnd;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "state", nullable = false)
    private boolean state;

    @Column(name = "message", nullable = true)
    private String message;

//    @OneToMany(mappedBy = "reserval", cascade = CascadeType.ALL)
//    private List<TableReservalRelation> reservals;

}
