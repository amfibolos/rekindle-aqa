package com.rekindle.book.store.domain.application.crud

import com.rekindle.book.store.domain.valueobjects.ValueObject

interface Delete<T : ValueObject, U>  {
    fun delete(t: T): U
    fun deleteSuccessfully(t: T)
}