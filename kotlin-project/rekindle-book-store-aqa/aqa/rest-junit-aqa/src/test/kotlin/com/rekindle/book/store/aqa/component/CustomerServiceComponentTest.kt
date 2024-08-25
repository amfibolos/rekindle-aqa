package com.rekindle.book.store.aqa.component

import com.google.inject.Inject
import com.rekindle.book.store.aqa.di.AdminExtension
import com.rekindle.book.store.domain.errors.CustomerErrors
import com.rekindle.book.store.domain.factory.FactoryKey.CUSTOMER
import com.rekindle.book.store.domain.factory.FactoryKit
import com.rekindle.book.store.domain.factory.customer.CustomerKey.STD_USER
import com.rekindle.book.store.domain.tags.ComponentTest
import com.rekindle.book.store.domain.valueobjects.Customer
import com.rekindle.book.store.serenity.rest.controllers.customer.CustomerController
import org.apache.http.HttpStatus
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expect
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEmpty

@ComponentTest
@ExtendWith(AdminExtension::class)
internal class CustomerServiceComponentTest @Inject constructor(
    private var factoryKit: FactoryKit,
    private var customerController: CustomerController
) {
    private val customersList: List<Customer> = mutableListOf()

    @AfterEach
    fun clean() {
        if (customersList.isNotEmpty()) {
            customersList.forEach {
                customerController.deleteSuccessfully(it)
            }
        }
    }

    @Test
    fun `create new user in rekindle bookstore`() {
        var customer = factoryKit.factory<Customer>(CUSTOMER).create(STD_USER)
        customer = customerController.postSuccessfully(customer)
        customersList.addFirst(customer)
        expectThat(customer.id!!).isNotEmpty()
    }

    @Test
    fun `get user from rekindle bookstore`() {
        var customer = factoryKit.factory<Customer>(CUSTOMER).create(STD_USER)
        customer = customerController.postSuccessfully(customer)
        customersList.addFirst(customer)
        var fetchedCustomer = customerController.getSuccessfully(customer.id!!)
        expect {
            that(customer.id).isEqualTo(fetchedCustomer.id)
            that(customer.username).isEqualTo(fetchedCustomer.username)
            that(customer.firstName).isEqualTo(fetchedCustomer.firstName)
            that(customer.lastName).isEqualTo(fetchedCustomer.lastName)
        }
    }

    @Test
    fun `fetch all customers from rekindle bookstore`() {
        val customers = customerController.getAllSuccessfully()
        expectThat(customers)
            .isNotEmpty()
    }

    @Test
    fun `update customer from rekindle bookstore`() {
        var customer = factoryKit.factory<Customer>(CUSTOMER).create(STD_USER)
        customer = customerController.postSuccessfully(customer)
        customer.username = "EditedUsername"
        customerController.putSuccessfully(customer)
        val fetchedCustomer = customerController.getSuccessfully(customer.id!!)
        customersList.addFirst(fetchedCustomer)
        expect {
            that(customer.id).isEqualTo(fetchedCustomer.id)
            that(customer.username).isEqualTo(fetchedCustomer.username)
            that(customer.firstName).isEqualTo(fetchedCustomer.firstName)
            that(customer.lastName).isEqualTo(fetchedCustomer.lastName)
        }
    }

    @Test
    fun `delete customer from rekindle bookstore`() {
        var customer = factoryKit.factory<Customer>(CUSTOMER).create(STD_USER)
        customer = customerController.postSuccessfully(customer)
        customerController.deleteSuccessfully(customer)
        var response = customerController.get(customer.id!!)
        expect {
            that(response.statusCode()).isEqualTo(HttpStatus.SC_BAD_REQUEST)
            that(
                response.body().jsonPath().get("errorMessage") as String
            ).isEqualTo(CustomerErrors.CUSTOMER_NOT_FOUND)
        }
    }

}