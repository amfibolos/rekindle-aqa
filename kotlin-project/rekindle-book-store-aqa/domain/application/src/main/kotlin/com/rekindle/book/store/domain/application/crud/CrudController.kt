package com.rekindle.book.store.domain.application.crud

import com.rekindle.book.store.domain.valueobjects.ValueObject

interface CrudController<T : ValueObject, U> : Get<T, U>, GetAll<T, U>, Post<T, U>, Put<T, U>,
    Delete<T, U> {

    override fun get(param: String): U
    override fun getSuccessfully(param: String): T
    override fun getAll(param: String): U
    override fun getAllSuccessfully(param: String): List<T>
    override fun post(t: T): U
    override fun postSuccessfully(t: T): T
    override fun put(t: T): U
    override fun putSuccessfully(t: T)
    override fun delete(t: T): U
    override fun deleteSuccessfully(t: T)
}