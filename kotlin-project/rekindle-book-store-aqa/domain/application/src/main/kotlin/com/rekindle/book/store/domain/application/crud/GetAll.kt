package com.rekindle.book.store.domain.application.crud

import com.rekindle.book.store.domain.valueobjects.ValueObject

interface GetAll<T : ValueObject, U> {
    fun getAll(param: String = ""): U
    fun getAllSuccessfully(param: String = ""): List<T>
}