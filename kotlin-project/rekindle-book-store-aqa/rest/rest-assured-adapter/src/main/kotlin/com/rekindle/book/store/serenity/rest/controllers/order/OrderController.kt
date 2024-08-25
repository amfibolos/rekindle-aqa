package com.rekindle.book.store.serenity.rest.controllers.order

import com.rekindle.book.store.domain.application.crud.CrudController
import com.rekindle.book.store.domain.valueobjects.Order
import io.restassured.response.Response

interface OrderController : CrudController<Order, Response> {
}