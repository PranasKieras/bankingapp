package com.banking.app.service.impl

import com.banking.app.controller.request.DepositRequest
import com.banking.app.exception.UserNotFoundException
import com.banking.app.repository.AccountEventRepository
import com.banking.app.repository.UserRepository
import com.banking.app.repository.entity.AccountEvent
import com.banking.app.repository.entity.User
import spock.lang.Specification

class DepositServiceImplSpec extends Specification {

    DepositServiceImpl depositService = new DepositServiceImpl()

    UserRepository userRepository = Mock()

    AccountEventRepository accountEventRepository = Mock()

    DepositRequest depositRequest

    def setup() {
        depositService.userRepository = userRepository
        depositService.accountEventRepository = accountEventRepository
        depositRequest = new DepositRequest()
    }

    def "deposit calls AccountEventRepository when data is valid"() {
        given:
        depositRequest.email = "name@domain.com"
        depositRequest.password = "password"
        depositRequest.amount = BigDecimal.TEN
        def user = createUser("name@domain.com", "password", BigDecimal.TEN)
        userRepository.findByEmail("name@domain.com") >> Optional.of(user)

        when:
        depositService.deposit(depositRequest)

        then:
        1 * userRepository.save(createUser("name@domain.com", "password", BigDecimal.TEN))
        1 * accountEventRepository.save(createAccountEvent(BigDecimal.TEN, user))
    }

    def "deposit calls throws UserNotFoundException when user does not exist"() {
        given:
        depositRequest.email = "name@domain.com"
        depositRequest.password = "password"
        depositRequest.amount = BigDecimal.TEN
        def user = createUser("name@domain.com", "password", BigDecimal.TEN)
        userRepository.findByEmail("name@domain.com") >> Optional.empty()

        when:
        depositService.deposit(depositRequest)

        then:
        thrown UserNotFoundException
    }

    def createUser(String email, String password, BigDecimal balance) {
        User user = new User()
        user.email = email
        user.password = password
        user.balance = balance
        return user
    }

    def createAccountEvent(BigDecimal amount, User user) {
        AccountEvent event = new AccountEvent()
        event.amount = amount
        event.user = user
        return event
    }
}
