package com.rekindle.book.store.aqa.component

import com.google.inject.Inject
import com.rekindle.book.store.aqa.di.AdminExtension
import com.rekindle.book.store.domain.assertions.isEqualToEither
import com.rekindle.book.store.domain.errors.CustomerErrors
import com.rekindle.book.store.domain.errors.GenericErrors
import com.rekindle.book.store.domain.factory.FactoryKey
import com.rekindle.book.store.domain.factory.FactoryKit
import com.rekindle.book.store.domain.factory.customer.CustomerKey
import com.rekindle.book.store.domain.tags.ComponentTest
import com.rekindle.book.store.domain.valueobjects.Customer
import com.rekindle.book.store.serenity.rest.controllers.customer.CustomerController
import org.apache.http.HttpStatus
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expect
import strikt.assertions.isEqualTo
import java.util.*

@ComponentTest
@ExtendWith(AdminExtension::class)
internal class CustomerServiceComponentNegativeTest @Inject constructor(
    private var factoryKit: FactoryKit,
    private var customerController: CustomerController
) {

    @Test
    fun `verify cannot create customer with empty values`() {
        val customer = Customer("", "", "", "")
        val response = customerController.post(customer)
        expect {
            that(response.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST)
        }
        expect {
            that(
                response.body().jsonPath().get<String?>("firstName")
            ).isEqualToEither(GenericErrors.NOT_NULL, GenericErrors.NOT_BLANK)
            that(
                response.body().jsonPath().get<String?>("lastName")
            ).isEqualToEither(GenericErrors.NOT_NULL, GenericErrors.NOT_BLANK)
            that(
                response.body().jsonPath().get<String?>("username")
            ).isEqualToEither(GenericErrors.NOT_NULL, GenericErrors.NOT_BLANK)
        }
    }

    @Test
    fun `verify cannot create customer with null values`() {
        var customer = Customer(null, null, null, null)
        val response = customerController.post(customer)
        expect {
            that(response.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST)
        }
        expect {
            that(
                response.body().jsonPath().get<String?>("firstName")
            ).isEqualToEither(GenericErrors.NOT_NULL, GenericErrors.NOT_BLANK)
            that(
                response.body().jsonPath().get<String?>("lastName")
            ).isEqualToEither(GenericErrors.NOT_NULL, GenericErrors.NOT_BLANK)
            that(
                response.body().jsonPath().get<String?>("username")
            ).isEqualToEither(GenericErrors.NOT_NULL, GenericErrors.NOT_BLANK)
        }
    }

    @Test
    fun `verify cannot get non-existing customer`() {
        var customer =
            factoryKit.factory<Customer>(FactoryKey.CUSTOMER).create(CustomerKey.STD_USER)
        customer.id = UUID.randomUUID().toString()
        val response = customerController.get(customer.id!!)
        expect {
            that(response.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST)
            that(
                response.body().jsonPath().get("errorMessage") as String
            ).isEqualTo(CustomerErrors.CUSTOMER_NOT_FOUND)
        }
    }

    @Test
    fun `verify cannot delete non-existing customer`() {
        var customer =
            factoryKit.factory<Customer>(FactoryKey.CUSTOMER).create(CustomerKey.STD_USER)
        customer.id = UUID.randomUUID().toString()
        val response = customerController.delete(customer)
        expect {
            that(response.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST)
            that(
                response.body().jsonPath().get("errorMessage") as String
            ).isEqualTo(CustomerErrors.CUSTOMER_NOT_FOUND)
        }
    }

    @Test
    fun `verify cannot update non-existing customer`() {
        var customer =
            factoryKit.factory<Customer>(FactoryKey.CUSTOMER).create(CustomerKey.STD_USER)
        customer.id = UUID.randomUUID().toString()
        val response = customerController.put(customer)
        expect {
            that(response.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST)
            that(
                response.body().jsonPath().get("errorMessage") as String
            ).isEqualTo(CustomerErrors.CUSTOMER_NOT_FOUND)
        }
    }

}