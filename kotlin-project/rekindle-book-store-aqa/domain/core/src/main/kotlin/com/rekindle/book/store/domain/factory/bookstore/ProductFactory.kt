package com.rekindle.book.store.domain.factory.bookstore

import com.rekindle.book.store.domain.exception.ValueObjectNotFoundException
import com.rekindle.book.store.domain.factory.Factory
import com.rekindle.book.store.domain.factory.ValueKey
import com.rekindle.book.store.domain.utilities.randomPriceLessThanThousand
import com.rekindle.book.store.domain.valueobjects.Product
import io.github.serpro69.kfaker.Faker
import java.util.function.Supplier

class ProductFactory : Factory<Product> {
    private val faker = Faker()

    private val map: Map<ValueKey, Supplier<Product>> =
        mapOf(
            ProductKey.STD_PRODUCT_AVAILABLE to Supplier {
                Product(
                    name = faker.book.title(),
                    price = randomPriceLessThanThousand(),
                    available = true
                )
            },
            ProductKey.STD_PRODUCT_NON_AVAILABLE to Supplier {
                Product(
                    name = faker.book.title(),
                    price = randomPriceLessThanThousand(),
                    available = false
                )
            }
        )

    override fun create(key: ValueKey): Product {
        if (map.containsKey(key)) {
            return map[key]!!.get()
        } else {
            throw ValueObjectNotFoundException("Product identified by $key has not yet been implemented")
        }
    }


}