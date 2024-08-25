package com.rekindle.book.store.serenity.rest.controllers.customer

import com.rekindle.book.store.domain.application.crud.CrudController
import com.rekindle.book.store.domain.valueobjects.Customer
import io.restassured.response.Response

interface CustomerController : CrudController<Customer, Response> {
}