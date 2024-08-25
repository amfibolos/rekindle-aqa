package com.rekindle.book.store.serenity.rest.specification

import io.restassured.specification.RequestSpecification

interface Specification {
    fun getAuthToken(): String
    fun basicSpec(): RequestSpecification

}