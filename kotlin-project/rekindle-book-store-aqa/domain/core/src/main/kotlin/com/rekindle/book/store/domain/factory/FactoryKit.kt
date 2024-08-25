package com.rekindle.book.store.domain.factory

import com.rekindle.book.store.domain.valueobjects.ValueObject

interface FactoryKit {
    fun <T : ValueObject> factory(key: ValueKey): Factory<T>
}