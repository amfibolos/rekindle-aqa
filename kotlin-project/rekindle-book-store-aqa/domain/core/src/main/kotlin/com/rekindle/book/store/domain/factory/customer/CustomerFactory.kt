package com.rekindle.book.store.domain.factory.customer

import com.rekindle.book.store.domain.exception.ValueObjectNotFoundException
import com.rekindle.book.store.domain.factory.Factory
import com.rekindle.book.store.domain.factory.ValueKey
import com.rekindle.book.store.domain.valueobjects.Customer
import io.github.serpro69.kfaker.Faker
import java.util.function.Supplier

class CustomerFactory : Factory<Customer> {
    private val faker = Faker()

    private val map: Map<ValueKey, Supplier<Customer>> =
        mapOf(
            CustomerKey.STD_USER to Supplier {
                Customer(
                    "",
                    faker.name.name(),
                    faker.name.firstName(),
                    faker.name.lastName()
                )
            }
        )

    override fun create(key: ValueKey): Customer {
        if (map.containsKey(key)) {
            return map[key]!!.get()
        } else {
            throw ValueObjectNotFoundException("Customer identified by $key has not yet been implemented")
        }
    }


}