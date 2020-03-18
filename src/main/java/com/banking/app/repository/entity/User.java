package com.banking.app.repository.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)

    private String email;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "decimal(7,2) not null check (balance >= 0)")
    private BigDecimal balance = BigDecimal.ZERO;

    @OneToMany(mappedBy = "user")
    @EqualsAndHashCode.Exclude
    private Set<AccountEvent> accountEvents;

    @Version
    private Integer version;
}
