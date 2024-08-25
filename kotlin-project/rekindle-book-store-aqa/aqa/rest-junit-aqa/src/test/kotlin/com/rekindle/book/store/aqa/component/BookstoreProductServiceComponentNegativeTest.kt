package com.rekindle.book.store.aqa.component

import com.google.inject.Inject
import com.rekindle.book.store.aqa.di.AdminExtension
import com.rekindle.book.store.domain.assertions.isEqualToEither
import com.rekindle.book.store.domain.errors.GenericErrors
import com.rekindle.book.store.domain.errors.ProductErrors
import com.rekindle.book.store.domain.factory.FactoryKey.BOOKSTORE
import com.rekindle.book.store.domain.factory.FactoryKey.PRODUCT
import com.rekindle.book.store.domain.factory.FactoryKit
import com.rekindle.book.store.domain.factory.bookstore.BookstoreKey.STD_BOOKSTORE_ACTIVE
import com.rekindle.book.store.domain.factory.bookstore.ProductKey.STD_PRODUCT_AVAILABLE
import com.rekindle.book.store.domain.tags.ComponentTest
import com.rekindle.book.store.domain.valueobjects.Bookstore
import com.rekindle.book.store.domain.valueobjects.Product
import com.rekindle.book.store.serenity.rest.controllers.bookstore.BookstoreController
import com.rekindle.book.store.serenity.rest.controllers.bookstore.BookstoreProductController
import org.apache.http.HttpStatus
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expect
import strikt.assertions.isEqualTo
import java.util.UUID

@ComponentTest
@ExtendWith(AdminExtension::class)
class BookstoreProductServiceComponentNegativeTest @Inject constructor(
    private var factoryKit: FactoryKit,
    private var productController: BookstoreProductController,
    private var bookstoreController: BookstoreController
) {
    private val bookstoreList: List<Bookstore> = mutableListOf()
    private var bookstore: Bookstore =
        factoryKit.factory<Bookstore>(BOOKSTORE).create(STD_BOOKSTORE_ACTIVE)

    init {
        bookstore = bookstoreController.postSuccessfully(bookstore)
        bookstoreList.addFirst(bookstore)
    }

    @AfterEach
    fun clean() {
        if (bookstoreList.isNotEmpty()) {
            bookstoreList.forEach {
                bookstoreController.deleteSuccessfully(it)
            }
        }
    }

    @Test
    fun `verify cannot create product with empty values`() {
        val product = Product(name = "", price = null, available = null, bookstoreId = bookstore.id)
        val response = productController.post(product)
        expect {
            that(response.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST)
            that(
                response.body().jsonPath().get<String?>("name")
            ).isEqualToEither(GenericErrors.NOT_NULL, GenericErrors.NOT_BLANK)
            that(
                response.body().jsonPath().get<String?>("available")
            ).isEqualTo(GenericErrors.NOT_NULL)
            that(
                response.body().jsonPath().get<String?>("price")
            ).isEqualTo(GenericErrors.NOT_NULL)
        }
    }

    @Test
    fun `verify cannot create product with null values`() {
        val product =
            Product(name = null, price = null, available = null, bookstoreId = bookstore.id)
        val response = productController.post(product)
        expect {
            that(response.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST)
            that(
                response.body().jsonPath().get<String?>("name")
            ).isEqualToEither(GenericErrors.NOT_BLANK, GenericErrors.NOT_NULL)
            that(
                response.body().jsonPath().get<String?>("available")
            ).isEqualTo(GenericErrors.NOT_NULL)
            that(
                response.body().jsonPath().get<String?>("price")
            ).isEqualTo(GenericErrors.NOT_NULL)
        }
    }

    @Test
    fun `verify cannot get non-existing product`() {
        val product = factoryKit.factory<Product>(PRODUCT).create(STD_PRODUCT_AVAILABLE)
        product.id = UUID.randomUUID().toString()
        val response = productController.get(product.id!!)
        expect {
            that(response.statusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND)
            that(
                response.body().jsonPath().get("errorMessage") as String
            ).isEqualTo(ProductErrors.PRODUCT_NOT_FOUND)
        }
    }

    @Test
    fun `verify cannot delete non-existing product`() {
        val product = factoryKit.factory<Product>(PRODUCT).create(STD_PRODUCT_AVAILABLE)
        product.bookstoreId = bookstore.id
        product.id = UUID.randomUUID().toString()
        val response = productController.delete(product)
        expect {
            that(response.statusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND)
            that(
                response.body().jsonPath().get("errorMessage") as String
            ).isEqualTo(ProductErrors.PRODUCT_NOT_FOUND)
        }
    }

    @Test
    fun `verify cannot update non-existing customer`() {
        val product = factoryKit.factory<Product>(PRODUCT).create(STD_PRODUCT_AVAILABLE)
        product.bookstoreId = bookstore.id
        product.id = UUID.randomUUID().toString()
        val response = productController.put(product)
        expect {
            that(response.statusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND)
            that(
                response.body().jsonPath().get("errorMessage") as String
            ).isEqualTo(ProductErrors.PRODUCT_NOT_FOUND)
        }
    }
}