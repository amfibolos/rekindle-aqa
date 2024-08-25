package com.rekindle.book.store.serenity.rest.controllers.payment

import com.rekindle.book.store.domain.application.crud.Get
import com.rekindle.book.store.domain.valueobjects.Payment
import io.restassured.response.Response

interface PaymentController : Get<Payment, Response> {
}