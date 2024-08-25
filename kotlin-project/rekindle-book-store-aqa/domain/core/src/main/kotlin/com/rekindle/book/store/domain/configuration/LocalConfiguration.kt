package com.rekindle.book.store.domain.configuration

import com.rekindle.book.store.domain.configuration.endpoint.BookstoresEndpoint
import com.rekindle.book.store.domain.configuration.endpoint.CustomersEndpoint
import com.rekindle.book.store.domain.configuration.endpoint.OrdersEndpoint
import com.rekindle.book.store.domain.configuration.endpoint.PaymentsEndpoint
import org.aeonbits.owner.ConfigCache

class LocalConfiguration : Configuration {

    override fun env(): Environment {
        return ConfigCache.getOrCreate(Environment::class.java)
    }

    override fun customerEndpoint(): CustomersEndpoint {
        return ConfigCache.getOrCreate(CustomersEndpoint::class.java)
    }

    override fun bookstoreEndpoint(): BookstoresEndpoint {
        return ConfigCache.getOrCreate(BookstoresEndpoint::class.java)
    }

    override fun orderEndpoint(): OrdersEndpoint {
        return ConfigCache.getOrCreate(OrdersEndpoint::class.java)
    }

    override fun paymentEndpoint(): PaymentsEndpoint {
        return ConfigCache.getOrCreate(PaymentsEndpoint::class.java)
    }

}