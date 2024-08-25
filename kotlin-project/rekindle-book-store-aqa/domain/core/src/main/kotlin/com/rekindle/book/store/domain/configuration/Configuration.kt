package com.rekindle.book.store.domain.configuration

import com.rekindle.book.store.domain.configuration.endpoint.BookstoresEndpoint
import com.rekindle.book.store.domain.configuration.endpoint.CustomersEndpoint
import com.rekindle.book.store.domain.configuration.endpoint.OrdersEndpoint
import com.rekindle.book.store.domain.configuration.endpoint.PaymentsEndpoint

interface Configuration {

    fun env() : Environment
    fun customerEndpoint(): CustomersEndpoint
    fun bookstoreEndpoint(): BookstoresEndpoint
    fun orderEndpoint(): OrdersEndpoint
    fun paymentEndpoint(): PaymentsEndpoint

}