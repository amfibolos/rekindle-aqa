package com.rekindle.book.store.domain.factory

import com.rekindle.book.store.domain.exception.FactoryNotImplementedException
import com.rekindle.book.store.domain.factory.bookstore.BookstoreFactory
import com.rekindle.book.store.domain.factory.bookstore.ProductFactory
import com.rekindle.book.store.domain.factory.customer.CustomerFactory
import com.rekindle.book.store.domain.valueobjects.ValueObject

class ValueObjectFactoryKit : FactoryKit {
    private var factoryKit: Map<ValueKey, Factory<*>> = mapOf(
        FactoryKey.CUSTOMER to CustomerFactory(),
        FactoryKey.BOOKSTORE to BookstoreFactory(),
        FactoryKey.PRODUCT to ProductFactory()
    )

    override fun <T : ValueObject> factory(key: ValueKey): Factory<T> {
        if (factoryKit.containsKey(key)) {
            return factoryKit[key] as Factory<T>
        } else {
            throw FactoryNotImplementedException("Factory identified by key $key has not yet been implemented")
        }
    }


}