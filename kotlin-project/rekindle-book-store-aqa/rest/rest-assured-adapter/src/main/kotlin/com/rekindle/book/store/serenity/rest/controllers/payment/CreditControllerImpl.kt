package com.rekindle.book.store.serenity.rest.controllers.payment

import com.google.inject.Inject
import com.rekindle.book.store.domain.configuration.Configuration
import com.rekindle.book.store.domain.filters.JacksonFilters
import com.rekindle.book.store.domain.valueobjects.Credit
import com.rekindle.book.store.serenity.rest.specification.Specification
import io.restassured.RestAssured
import io.restassured.response.Response
import org.apache.http.HttpStatus

class CreditControllerImpl @Inject constructor(
    private val config: Configuration,
    private val spec: Specification
) : CreditController {
    override fun getAll(param: String): Response {
        return RestAssured
            .given(spec.basicSpec())
            .pathParams(mutableMapOf("customerId" to param))
            .auth()
            .preemptive()
            .oauth2(spec.getAuthToken())
            .get(config.paymentEndpoint().paymentCreditHistoryByCustomerId())
    }

    override fun getAllSuccessfully(param: String): List<Credit> {
        return this.getAll(param)
            .then()
            .statusCode(HttpStatus.SC_OK)
            .extract()
            .jsonPath()
            .getList(".")
    }

    override fun post(t: Credit): Response {
        return RestAssured
            .given(spec.basicSpec())
            .body(t.toJsonStringFiltered(JacksonFilters.creditFilter))
            .auth()
            .preemptive()
            .oauth2(spec.getAuthToken())
            .post(config.paymentEndpoint().paymentsCredit())
    }

    override fun postSuccessfully(t: Credit): Credit {
        val id : String = this.post(t)
            .then()
            .statusCode(HttpStatus.SC_CREATED)
            .extract()
            .body()
            .jsonPath()
            .get(".")
        t.id = id
        return t
    }
}