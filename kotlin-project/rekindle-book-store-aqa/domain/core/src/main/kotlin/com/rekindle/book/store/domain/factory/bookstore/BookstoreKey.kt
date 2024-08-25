package com.rekindle.book.store.domain.factory.bookstore

import com.rekindle.book.store.domain.factory.ValueKey

enum class BookstoreKey : ValueKey {
    STD_BOOKSTORE_ACTIVE,
    STD_BOOKSTORE_INACTIVE;

    override fun getKey(): ValueKey {
        return this;
    }
}