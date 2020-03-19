package com.banking.app.repository.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
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

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @Version
    private Integer version;
}
