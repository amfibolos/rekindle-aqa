package com.rekindle.book.store.serenity.rest.controllers.payment

import com.google.inject.Inject
import com.rekindle.book.store.domain.configuration.Configuration
import com.rekindle.book.store.domain.valueobjects.Payment
import com.rekindle.book.store.serenity.rest.specification.Specification
import io.restassured.RestAssured
import io.restassured.response.Response
import org.apache.http.HttpStatus

class PaymentControllerImpl @Inject constructor(
    private val config: Configuration,
    private val spec: Specification
) : PaymentController {
    override fun get(param: String): Response {
        return RestAssured
            .given(spec.basicSpec())
            .pathParams(mutableMapOf("orderId" to param))
            .auth()
            .preemptive()
            .oauth2(spec.getAuthToken())
            .get(config.paymentEndpoint().paymentByOrderId())
    }

    override fun getSuccessfully(param: String): Payment {
        return this.get(param)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .body()
            .`as`(Payment::class.java)
    }
}