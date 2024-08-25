package com.rekindle.book.store.domain.factory

interface Factory<T> {

    fun create(key : ValueKey) : T

}