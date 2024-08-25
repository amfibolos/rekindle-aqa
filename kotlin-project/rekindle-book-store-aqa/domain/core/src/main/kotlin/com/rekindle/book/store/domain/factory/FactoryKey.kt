package com.rekindle.book.store.domain.factory

enum class FactoryKey : ValueKey {
    CUSTOMER,
    BOOKSTORE,
    PRODUCT;

    override fun getKey(): ValueKey {
        return this;
    }

}