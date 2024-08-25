package com.rekindle.book.store.serenity.rest.specification

import com.google.inject.Inject
import com.rekindle.book.store.domain.configuration.Configuration
import io.restassured.RestAssured
import io.restassured.authentication.BasicAuthScheme
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.apache.http.HttpStatus

class AdminSpecification @Inject constructor(private val config: Configuration) : Specification {

    @Volatile
    private var token: String? = null

    override fun getAuthToken(): String {
        if (token == null) {
            synchronized(this) {
                if (token == null) {
                    this.token = RestAssured
                        .given()
                        .spec(oauth2AdminSpec())
                        .auth()
                        .preemptive()
                        .basic(config.env().clientId(), config.env().clientSecret())
                        .post()
                        .then()
                        .statusCode(HttpStatus.SC_OK)
                        .extract()
                        .path("access_token")
                    return token!!
                }
            }
        }
        return token!!
    }


    override fun basicSpec(): RequestSpecification {
        return RequestSpecBuilder()
            .setBaseUri(config.env().baseUrl())
            .addFilters(listOf(RequestLoggingFilter(), ResponseLoggingFilter()))
            .setRelaxedHTTPSValidation()
            .setContentType(ContentType.JSON)
            .build()
    }

    private fun oauth2AdminSpec(): RequestSpecification {
        return RequestSpecBuilder()
            .setBaseUri(config.env().authTokenUrl())
            .addFilters(listOf(RequestLoggingFilter(), ResponseLoggingFilter()))
            .setRelaxedHTTPSValidation()
            .addFormParam("grant_type", config.env().grantType())
            .addFormParam("scope", config.env().scopesAdmin())
            .setContentType("application/x-www-form-urlencoded")
            .build()
    }

}