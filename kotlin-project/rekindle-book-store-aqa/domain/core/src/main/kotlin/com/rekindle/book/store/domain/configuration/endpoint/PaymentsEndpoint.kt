package com.rekindle.book.store.domain.configuration.endpoint

import org.aeonbits.owner.Config
import org.aeonbits.owner.Config.Key
import org.aeonbits.owner.Config.Sources

@Sources(value = ["classpath:endpoints/payments-endpoints.properties"])
interface PaymentsEndpoint : Config {

    @Key("payments.credit")
    fun paymentsCredit(): String

    @Key("payment.by.order.id")
    fun paymentByOrderId(): String

    @Key("payment.credit.history.by.customer.id")
    fun paymentCreditHistoryByCustomerId(): String

}