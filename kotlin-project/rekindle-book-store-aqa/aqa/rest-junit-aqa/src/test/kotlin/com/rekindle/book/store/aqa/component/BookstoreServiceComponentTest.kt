package com.rekindle.book.store.aqa.component

import com.google.inject.Inject
import com.rekindle.book.store.aqa.di.AdminExtension
import com.rekindle.book.store.domain.errors.BookstoreErrors
import com.rekindle.book.store.domain.factory.FactoryKey.BOOKSTORE
import com.rekindle.book.store.domain.factory.FactoryKit
import com.rekindle.book.store.domain.factory.bookstore.BookstoreKey.STD_BOOKSTORE_ACTIVE
import com.rekindle.book.store.domain.factory.bookstore.BookstoreKey.STD_BOOKSTORE_INACTIVE
import com.rekindle.book.store.domain.tags.ComponentTest
import com.rekindle.book.store.domain.valueobjects.Bookstore
import com.rekindle.book.store.serenity.rest.controllers.bookstore.BookstoreController
import org.apache.http.HttpStatus
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEmpty
import strikt.assertions.isNullOrBlank

@ComponentTest
@ExtendWith(AdminExtension::class)
internal class BookstoreServiceComponentTest @Inject constructor(
    private var factoryKit: FactoryKit,
    private var bookstoreController: BookstoreController
) {

    private val bookstoreList: List<Bookstore> = mutableListOf()

    @AfterEach
    fun clean(){
        if (bookstoreList.isNotEmpty()){
            bookstoreList.forEach {
                bookstoreController.deleteSuccessfully(it)
            }
        }
    }

    @Test
    fun `create new bookstore in Rekindle Bookstore Network`() {
        var bookstore = factoryKit.factory<Bookstore>(BOOKSTORE).create(STD_BOOKSTORE_ACTIVE)
        bookstore = bookstoreController.postSuccessfully(bookstore)
        bookstoreList.addFirst(bookstore)
        expectThat(bookstore.id).not().isNullOrBlank()
    }

    @Test
    fun `get new bookstore from Rekindle Bookstore Network`() {
        var bookstore = factoryKit.factory<Bookstore>(BOOKSTORE).create(STD_BOOKSTORE_INACTIVE)
        bookstore = bookstoreController.postSuccessfully(bookstore)
        bookstoreList.addFirst(bookstore)
        val bookstoreFetched = bookstoreController.getSuccessfully(bookstore.id!!)
        expect {
            that(bookstore.id).isEqualTo(bookstoreFetched.id)
            that(bookstore.name).isEqualTo(bookstoreFetched.name)
            that(bookstore.isActive).isEqualTo(bookstoreFetched.isActive)
        }
    }

    @Test
    fun `get all bookstores from Rekindle Bookstore Network`() {
        val bookstores = bookstoreController.getAllSuccessfully()
        expectThat(bookstores).isNotEmpty()
    }

    @Test
    fun `delete bookstore from Rekindle Bookstore Network`() {
        var bookstore = factoryKit.factory<Bookstore>(BOOKSTORE).create(STD_BOOKSTORE_ACTIVE)
        bookstore = bookstoreController.postSuccessfully(bookstore)
        bookstoreController.deleteSuccessfully(bookstore)
        val response = bookstoreController.get(bookstore.id!!)
        expect {
            that(response.statusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND)
            that(
                response.body().jsonPath().get("errorMessage") as String
            ).isEqualTo(BookstoreErrors.BOOKSTORE_NOT_FOUND)
        }
    }

    @Test
    fun `update bookstore data in Rekindle Bookstore Network`() {
        var bookstore = factoryKit.factory<Bookstore>(BOOKSTORE).create(STD_BOOKSTORE_ACTIVE)
        bookstore = bookstoreController.postSuccessfully(bookstore)
        bookstoreList.addFirst(bookstore)
        bookstore.name = "EditedName"
        bookstore.isActive = false
        bookstoreController.putSuccessfully(bookstore)
        val bookstoreFetched = bookstoreController.getSuccessfully(bookstore.id!!)
        expect {
            that(bookstore.id).isEqualTo(bookstoreFetched.id)
            that(bookstore.name).isEqualTo(bookstoreFetched.name)
            that(bookstore.isActive).isEqualTo(bookstoreFetched.isActive)
        }
    }

}