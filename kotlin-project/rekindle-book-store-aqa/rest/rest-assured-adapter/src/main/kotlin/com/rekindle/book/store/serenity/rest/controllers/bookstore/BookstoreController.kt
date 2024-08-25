package com.rekindle.book.store.serenity.rest.controllers.bookstore

import com.rekindle.book.store.domain.application.crud.CrudController
import com.rekindle.book.store.domain.valueobjects.Bookstore
import io.restassured.response.Response

interface BookstoreController : CrudController<Bookstore, Response> {
}