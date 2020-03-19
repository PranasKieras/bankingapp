package com.banking.app.repository.entity;

import com.banking.app.operation.Operation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "account_events")
public class AccountEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column
    private BigDecimal amount;

    @Column
    private Operation operation;

    @Column(name="balance_after_operation")
    private BigDecimal balanceAfterOperation;

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable=false)
    @EqualsAndHashCode.Exclude
    private User user;


}
