package com.rekindle.book.store.domain.factory.bookstore

import com.rekindle.book.store.domain.factory.ValueKey

enum class ProductKey : ValueKey {
    STD_PRODUCT_AVAILABLE,
    STD_PRODUCT_NON_AVAILABLE;

    override fun getKey(): ValueKey {
        return this
    }
}