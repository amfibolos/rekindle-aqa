package com.rekindle.book.store.serenity.rest.controllers.payment

import com.rekindle.book.store.domain.application.crud.GetAll
import com.rekindle.book.store.domain.application.crud.Post
import com.rekindle.book.store.domain.valueobjects.Credit
import io.restassured.response.Response

interface CreditController : GetAll<Credit, Response>, Post<Credit, Response> {
}