package com.banking.app.repository.entity;

import javax.persistence.*;

@Entity
@Table(name = "account_events")
public class AccountEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id", nullable=false)
    private User user;


}
