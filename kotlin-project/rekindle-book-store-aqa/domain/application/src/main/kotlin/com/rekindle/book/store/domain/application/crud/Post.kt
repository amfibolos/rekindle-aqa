package com.rekindle.book.store.domain.application.crud

import com.rekindle.book.store.domain.valueobjects.ValueObject

interface Post<T : ValueObject, U>  {
    fun post(t: T): U
    fun postSuccessfully(t: T): T
}