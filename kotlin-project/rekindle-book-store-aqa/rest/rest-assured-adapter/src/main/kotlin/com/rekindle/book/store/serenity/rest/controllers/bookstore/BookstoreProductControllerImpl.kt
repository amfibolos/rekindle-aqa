package com.rekindle.book.store.serenity.rest.controllers.bookstore

import com.google.inject.Inject
import com.rekindle.book.store.domain.configuration.Configuration
import com.rekindle.book.store.domain.filters.JacksonFilters
import com.rekindle.book.store.domain.valueobjects.Product
import com.rekindle.book.store.serenity.rest.specification.Specification
import io.restassured.RestAssured
import io.restassured.response.Response
import org.apache.http.HttpStatus

class BookstoreProductControllerImpl @Inject constructor(
    private val config: Configuration,
    private val spec: Specification
) : BookstoreProductController {
    override fun get(param: String): Response {
        return RestAssured
            .given(spec.basicSpec())
            .pathParams(mutableMapOf("productId" to param))
            .auth()
            .preemptive()
            .oauth2(spec.getAuthToken())
            .get(config.bookstoreEndpoint().bookstoreProductById())
    }

    override fun getSuccessfully(param: String): Product {
        return this.get(param)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .body()
            .`as`(Product::class.java)
    }

    override fun getAll(param: String): Response {
        return RestAssured
            .given(spec.basicSpec())
            .auth()
            .preemptive()
            .oauth2(spec.getAuthToken())
            .get(config.bookstoreEndpoint().bookstoreProducts())
    }

    override fun getAllSuccessfully(param: String): List<Product> {
        return this.getAll()
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .body()
            .jsonPath()
            .getList(".")
    }

    override fun post(t: Product): Response {
        return RestAssured
            .given(spec.basicSpec())
            .pathParams(mutableMapOf("bookstoreId" to t.bookstoreId))
            .auth()
            .preemptive()
            .oauth2(spec.getAuthToken())
            .body(t.toJsonStringFiltered(JacksonFilters.bookstoreProductFilter))
            .post(config.bookstoreEndpoint().bookstoreProductByStoreId())
    }

    override fun postSuccessfully(t: Product): Product {
        val id: String = this.post(t)
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .body()
            .jsonPath()
            .get(".")
        t.id = id
        return t
    }

    override fun put(t: Product): Response {
        return RestAssured
            .given(spec.basicSpec())
            .pathParams(mutableMapOf("productId" to t.id))
            .auth()
            .preemptive()
            .oauth2(spec.getAuthToken())
            .body(t.toJsonStringFiltered(JacksonFilters.bookstoreProductFilter))
            .put(config.bookstoreEndpoint().bookstoreProductById())
    }

    override fun putSuccessfully(t: Product) {
        this.put(t)
            .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
    }

    override fun delete(t: Product): Response {
        return RestAssured
            .given(spec.basicSpec())
            .pathParams(mutableMapOf("bookstoreId" to t.bookstoreId, "productId" to t.id))
            .auth()
            .preemptive()
            .oauth2(spec.getAuthToken())
            .delete(config.bookstoreEndpoint().bookstoreProductByStoreIdProductId())
    }

    override fun deleteSuccessfully(t: Product) {
        this.delete(t)
            .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
    }
}