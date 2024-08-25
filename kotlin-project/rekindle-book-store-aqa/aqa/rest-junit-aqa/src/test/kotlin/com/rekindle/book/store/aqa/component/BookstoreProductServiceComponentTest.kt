package com.rekindle.book.store.aqa.component

import com.google.inject.Inject
import com.rekindle.book.store.aqa.di.AdminExtension
import com.rekindle.book.store.domain.errors.ProductErrors
import com.rekindle.book.store.domain.factory.FactoryKey.BOOKSTORE
import com.rekindle.book.store.domain.factory.FactoryKey.PRODUCT
import com.rekindle.book.store.domain.factory.FactoryKit
import com.rekindle.book.store.domain.factory.bookstore.BookstoreKey
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
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEmpty
import strikt.assertions.isNullOrBlank

@ComponentTest
@ExtendWith(AdminExtension::class)
internal class BookstoreProductServiceComponentTest @Inject constructor(
    private var factoryKit: FactoryKit,
    private var productController: BookstoreProductController,
    private var bookstoreController: BookstoreController
) {

    private val productList: List<Product> = mutableListOf()
    private val bookstoreList: List<Bookstore> = mutableListOf()
    private var bookstore: Bookstore =
        factoryKit.factory<Bookstore>(BOOKSTORE).create(BookstoreKey.STD_BOOKSTORE_ACTIVE)

    init {
        bookstore = bookstoreController.postSuccessfully(bookstore)
        bookstoreList.addFirst(bookstore)
    }

    @AfterEach
    fun clean() {
        if (productList.isNotEmpty()) {
            productList.forEach {
                productController.deleteSuccessfully(it)
            }
        }
        if (bookstoreList.isNotEmpty()) {
            bookstoreList.forEach {
                bookstoreController.deleteSuccessfully(it)
            }
        }
    }

    @Test
    fun `create new product in Rekindle Bookstore Network`() {
        var product = factoryKit.factory<Product>(PRODUCT).create(STD_PRODUCT_AVAILABLE)
        product.bookstoreId = bookstore.id
        product = productController.postSuccessfully(product)
        productList.addFirst(product)
        expectThat(product.id).not().isNullOrBlank()
    }

    @Test
    fun `get new product from Rekindle Bookstore Network`() {
        var product = factoryKit.factory<Product>(PRODUCT).create(STD_PRODUCT_AVAILABLE)
        product.bookstoreId = bookstore.id
        product = productController.postSuccessfully(product)
        productList.addFirst(product)
        val productFetched = productController.getSuccessfully(product.id!!)
        expect {
            that(product.id).isEqualTo(productFetched.id)
            that(product.name).isEqualTo(productFetched.name)
            that(product.available).isEqualTo(productFetched.available)
        }
    }

    @Test
    fun `get all products from Rekindle Bookstore Network`() {
        val products = productController.getAllSuccessfully()
        expectThat(products).isNotEmpty()
    }

    @Test
    fun `delete product from Rekindle Bookstore Network`() {
        var product = factoryKit.factory<Product>(PRODUCT).create(STD_PRODUCT_AVAILABLE)
        product.bookstoreId = bookstore.id
        product = productController.postSuccessfully(product)
        productController.deleteSuccessfully(product)
        val response = productController.get(product.id!!)
        expect {
            that(response.statusCode()).isEqualTo(HttpStatus.SC_NOT_FOUND)
            that(
                response.body().jsonPath().get("errorMessage") as String
            ).isEqualTo(ProductErrors.PRODUCT_NOT_FOUND)
        }
    }

    @Test
    fun `update product data in Rekindle Bookstore Network`() {
        var product = factoryKit.factory<Product>(PRODUCT).create(STD_PRODUCT_AVAILABLE)
        product.bookstoreId = bookstore.id
        product = productController.postSuccessfully(product)
        productList.addFirst(product)
        product.name = "EditedName"
        product.available = false
        productController.putSuccessfully(product)
        val productFetched = productController.getSuccessfully(product.id!!)
        expect {
            that(product.id).isEqualTo(productFetched.id)
            that(product.name).isEqualTo(productFetched.name)
            that(product.available).isEqualTo(productFetched.available)
        }
    }

}