package com.rekindle.book.store.aqa.component

import com.google.inject.Inject
import com.rekindle.book.store.aqa.di.AdminExtension
import com.rekindle.book.store.domain.assertions.isEqualToEither
import com.rekindle.book.store.domain.errors.BookstoreErrors
import com.rekindle.book.store.domain.errors.GenericErrors
import com.rekindle.book.store.domain.factory.FactoryKey
import com.rekindle.book.store.domain.factory.FactoryKit
import com.rekindle.book.store.domain.factory.bookstore.BookstoreKey
import com.rekindle.book.store.domain.tags.ComponentTest
import com.rekindle.book.store.domain.valueobjects.Bookstore
import com.rekindle.book.store.serenity.rest.controllers.bookstore.BookstoreController
import org.apache.http.HttpStatus
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expect
import strikt.assertions.isEqualTo
import java.util.*

@ComponentTest
@ExtendWith(AdminExtension::class)
internal class BookstoreServiceComponentNegativeTest @Inject constructor(
    private var factoryKit: FactoryKit,
    private var bookstoreController: BookstoreController
) {

    @Test
    fun `verify cannot create bookstore with empty values`() {
        val bookstore = Bookstore("", "", null)
        val response = bookstoreController.post(bookstore)
        expect {
            that(response.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST)
            that(
                response.body().jsonPath().get<String?>("name")
            ).isEqualToEither(GenericErrors.NOT_NULL, GenericErrors.NOT_BLANK)
            that(
                response.body().jsonPath().get<String?>("isActive")
            ).isEqualToEither(GenericErrors.NOT_NULL, GenericErrors.NOT_BLANK)
        }
    }

    @Test
    fun `verify cannot create customer with null values`() {
        val bookstore = Bookstore(null, null, null)
        val response = bookstoreController.post(bookstore)
        expect {
            that(response.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST)
            that(
                response.body().jsonPath().get<String?>("name")
            ).isEqualToEither(GenericErrors.NOT_NULL, GenericErrors.NOT_BLANK)
            that(
                response.body().jsonPath().get<String?>("isActive")
            ).isEqualToEither(GenericErrors.NOT_NULL, GenericErrors.NOT_BLANK)
        }
    }

    @Test
    fun `verify cannot get non-existing customer`() {
        val bookstore = factoryKit.factory<Bookstore>(FactoryKey.BOOKSTORE)
            .create(BookstoreKey.STD_BOOKSTORE_ACTIVE)
        bookstore.id = UUID.randomUUID().toString()
        val response = bookstoreController.get(bookstore.id!!)
        expect {
            that(response.statusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND)
            that(
                response.body().jsonPath().get("errorMessage") as String
            ).isEqualTo(BookstoreErrors.BOOKSTORE_NOT_FOUND)
        }
    }

    @Test
    fun `verify cannot delete non-existing customer`() {
        val bookstore = factoryKit.factory<Bookstore>(FactoryKey.BOOKSTORE)
            .create(BookstoreKey.STD_BOOKSTORE_ACTIVE)
        bookstore.id = UUID.randomUUID().toString()
        val response = bookstoreController.delete(bookstore)
        expect {
            that(response.statusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND)
            that(
                response.body().jsonPath().get("errorMessage") as String
            ).isEqualTo(BookstoreErrors.BOOKSTORE_NOT_FOUND)
        }
    }

    @Test
    fun `verify cannot update non-existing customer`() {
        val bookstore = factoryKit.factory<Bookstore>(FactoryKey.BOOKSTORE)
            .create(BookstoreKey.STD_BOOKSTORE_ACTIVE)
        bookstore.id = UUID.randomUUID().toString()
        val response = bookstoreController.put(bookstore)
        expect {
            that(response.statusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND)
            that(
                response.body().jsonPath().get("errorMessage") as String
            ).isEqualTo(BookstoreErrors.BOOKSTORE_NOT_FOUND)
        }
    }

}