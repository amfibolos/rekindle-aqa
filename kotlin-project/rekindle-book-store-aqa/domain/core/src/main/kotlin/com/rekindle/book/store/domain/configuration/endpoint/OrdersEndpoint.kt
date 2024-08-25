package com.rekindle.book.store.domain.configuration.endpoint

import org.aeonbits.owner.Config
import org.aeonbits.owner.Config.Key
import org.aeonbits.owner.Config.Sources

@Sources(value = ["classpath:endpoints/orders-endpoints.properties"])
interface OrdersEndpoint : Config {

    @Key("orders")
    fun bookstores(): String

    @Key("order.by.tracking.id")
    fun orderByTrackingId(): String

}