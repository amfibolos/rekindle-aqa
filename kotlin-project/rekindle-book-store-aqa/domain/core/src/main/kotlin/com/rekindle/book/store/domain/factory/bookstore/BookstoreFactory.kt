package com.rekindle.book.store.domain.factory.bookstore

import com.rekindle.book.store.domain.exception.ValueObjectNotFoundException
import com.rekindle.book.store.domain.factory.Factory
import com.rekindle.book.store.domain.factory.ValueKey
import com.rekindle.book.store.domain.valueobjects.Bookstore
import io.github.serpro69.kfaker.Faker
import java.util.function.Supplier

class BookstoreFactory : Factory<Bookstore> {
    private val faker = Faker()

    private val map: Map<ValueKey, Supplier<Bookstore>> =
        mapOf(
            BookstoreKey.STD_BOOKSTORE_ACTIVE to Supplier {
                Bookstore(
                    "",
                    faker.company.name(),
                    true
                )
            },
            BookstoreKey.STD_BOOKSTORE_INACTIVE to Supplier {
                Bookstore(
                    "",
                    faker.company.name(),
                    false
                )
            }
        )

    override fun create(key: ValueKey): Bookstore {
        if (map.containsKey(key)) {
            return map[key]!!.get()
        }
        throw ValueObjectNotFoundException("Bookstore identified by $key has not yet been implemented")
    }


}