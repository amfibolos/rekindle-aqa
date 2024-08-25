package com.rekindle.book.store.domain.configuration.endpoint

import org.aeonbits.owner.Config
import org.aeonbits.owner.Config.Key
import org.aeonbits.owner.Config.Sources

@Sources(value = ["classpath:endpoints/customers-endpoints.properties"])
interface CustomersEndpoint : Config {

    @Key("customers")
    fun customers(): String

    @Key("customer.by.id")
    fun customerById(): String

}