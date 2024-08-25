package com.rekindle.book.store.domain.application.crud

import com.rekindle.book.store.domain.valueobjects.ValueObject

interface Get<T : ValueObject, U> {
    fun get(param: String = ""): U
    fun getSuccessfully(param: String = ""): T
}