package com.rekindle.book.store.serenity.rest.controllers.customer

import com.google.inject.Inject
import com.rekindle.book.store.domain.configuration.Configuration
import com.rekindle.book.store.domain.filters.JacksonFilters
import com.rekindle.book.store.domain.valueobjects.Customer
import com.rekindle.book.store.serenity.rest.specification.Specification
import io.restassured.RestAssured
import io.restassured.response.Response
import org.apache.http.HttpStatus


open class CustomerControllerImpl @Inject constructor(
    private val config: Configuration,
    private val spec: Specification
) : CustomerController {

    override fun get(param: String): Response {
        return RestAssured.given(spec.basicSpec())
            .pathParams(mutableMapOf("customerId" to param))
            .auth()
            .preemptive()
            .oauth2(spec.getAuthToken())
            .get(config.customerEndpoint().customerById())
    }

    override fun getSuccessfully(param: String): Customer {
        return this.get(param)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .response()
            .`as`(Customer::class.java)
    }

    override fun getAll(param: String): Response {
        return RestAssured.given(spec.basicSpec())
            .auth()
            .preemptive()
            .oauth2(spec.getAuthToken())
            .get(config.customerEndpoint().customers())
    }

    override fun getAllSuccessfully(param: String): List<Customer> {
        return this.getAll()
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .response()
            .jsonPath()
            .getList(".")
    }

    override fun post(t: Customer): Response {
        return RestAssured.given(spec.basicSpec())
            .body(t.toJsonStringFiltered(JacksonFilters.customerFilter))
            .auth()
            .preemptive()
            .oauth2(spec.getAuthToken())
            .post(config.customerEndpoint().customers())
    }

    override fun postSuccessfully(t: Customer): Customer {
        val id: String = this.post(t)
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .response()
            .jsonPath()
            .get("customerId")
        t.id = id
        return t
    }

    override fun put(t: Customer): Response {
        return RestAssured.given(spec.basicSpec())
            .pathParams(mutableMapOf("customerId" to t.id))
            .auth()
            .preemptive()
            .oauth2(spec.getAuthToken())
            .body(t.toJsonStringFiltered(JacksonFilters.customerFilter))
            .put(config.customerEndpoint().customerById())
    }

    override fun putSuccessfully(t: Customer) {
        this.put(t)
            .then()
            .statusCode(HttpStatus.SC_NO_CONTENT)
    }

    override fun delete(t: Customer): Response {
        return RestAssured.given(spec.basicSpec())
            .pathParams(mutableMapOf("customerId" to t.id))
            .auth()
            .preemptive()
            .oauth2(spec.getAuthToken())
            .delete(config.customerEndpoint().customerById())
    }

    override fun deleteSuccessfully(t: Customer) {
        this.delete(t)
            .then()
            .statusCode(HttpStatus.SC_OK)
    }

}