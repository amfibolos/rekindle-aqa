package com.rekindle.book.store.serenity.rest.controllers.bookstore

import com.rekindle.book.store.domain.application.crud.CrudController
import com.rekindle.book.store.domain.valueobjects.Product
import io.restassured.response.Response

interface BookstoreProductController : CrudController<Product, Response> {

}