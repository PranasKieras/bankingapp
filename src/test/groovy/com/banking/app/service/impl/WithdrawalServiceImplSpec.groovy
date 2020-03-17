package com.banking.app.service.impl

import com.banking.app.controller.request.WithdrawalRequest
import com.banking.app.exception.UserNotFoundException
import com.banking.app.repository.AccountEventRepository
import com.banking.app.repository.UserRepository
import com.banking.app.repository.entity.AccountEvent
import com.banking.app.repository.entity.User
import spock.lang.Specification

class WithdrawalServiceImplSpec extends Specification {

    WithdrawalServiceImpl withdrawalService = new WithdrawalServiceImpl()

    UserRepository userRepository = Mock()

    AccountEventRepository accountEventRepository = Mock()

    WithdrawalRequest withdrawalRequest

    def setup() {
        withdrawalService.userRepository = userRepository
        withdrawalService.accountEventRepository = accountEventRepository
        withdrawalRequest = new WithdrawalRequest()
    }

    def "withdraw calls AccountEventRepository when data is valid"() {
        given:
        withdrawalRequest.email = "name@domain.com"
        withdrawalRequest.password = "password"
        withdrawalRequest.amount = BigDecimal.TEN
        def user = createUser("name@domain.com", "password", BigDecimal.TEN)
        userRepository.findByEmail("name@domain.com") >> Optional.of(user)

        when:
        withdrawalService.withdraw(withdrawalRequest)

        then:
        1 * userRepository.save(createUser("name@domain.com", "password", BigDecimal.TEN))
        1 * accountEventRepository.save(createAccountEvent(BigDecimal.TEN, user))
    }

    def "withdraw calls throws UserNotFoundException when user does not exist"() {
        given:
        withdrawalRequest.email = "name@domain.com"
        withdrawalRequest.password = "password"
        withdrawalRequest.amount = BigDecimal.TEN
        def user = createUser("name@domain.com", "password", BigDecimal.TEN)
        userRepository.findByEmail("name@domain.com") >> Optional.empty()

        when:
        withdrawalService.withdraw(withdrawalRequest)

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
