package com.banking.app.repository;

import com.banking.app.repository.entity.AccountEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountEventRepository extends CrudRepository<AccountEvent, Long> {
}
