package com.rekindle.book.store.domain.factory.customer

import com.rekindle.book.store.domain.factory.ValueKey

enum class CustomerKey : ValueKey {
    STD_USER;

    override fun getKey(): ValueKey {
        return this;
    }
}