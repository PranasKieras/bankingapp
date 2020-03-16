package com.banking.app.repository;

import com.banking.app.repository.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
