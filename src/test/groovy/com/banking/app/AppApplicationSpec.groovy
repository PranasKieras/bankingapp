package com.banking.app

import com.banking.app.controller.BankingController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class AppApplicationSpec extends Specification {

    @Autowired
    private BankingController bankingController

    def "when context is loaded all beans are created"() {
        expect: "controller is created"
        bankingController
    }
}
