package com.rekindle.book.store.domain.application.crud

import com.rekindle.book.store.domain.valueobjects.ValueObject

interface Put<T : ValueObject, U>  {
    fun put(t: T): U
    fun putSuccessfully(t: T)
}