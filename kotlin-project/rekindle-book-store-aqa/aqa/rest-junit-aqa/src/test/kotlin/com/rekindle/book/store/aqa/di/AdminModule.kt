package com.rekindle.book.store.aqa.di

import com.google.inject.AbstractModule
import com.google.inject.Scopes
import com.rekindle.book.store.domain.configuration.Configuration
import com.rekindle.book.store.domain.configuration.LocalConfiguration
import com.rekindle.book.store.domain.factory.FactoryKit
import com.rekindle.book.store.domain.factory.ValueObjectFactoryKit
import com.rekindle.book.store.serenity.rest.controllers.bookstore.BookstoreController
import com.rekindle.book.store.serenity.rest.controllers.bookstore.BookstoreControllerImpl
import com.rekindle.book.store.serenity.rest.controllers.bookstore.BookstoreProductController
import com.rekindle.book.store.serenity.rest.controllers.bookstore.BookstoreProductControllerImpl
import com.rekindle.book.store.serenity.rest.controllers.customer.CustomerController
import com.rekindle.book.store.serenity.rest.controllers.customer.CustomerControllerImpl
import com.rekindle.book.store.serenity.rest.specification.AdminSpecification
import com.rekindle.book.store.serenity.rest.specification.Specification

class AdminModule : AbstractModule() {
    override fun configure() {
        bind(FactoryKit::class.java).to(ValueObjectFactoryKit::class.java).`in`(Scopes.SINGLETON)
        bind(CustomerController::class.java).to(CustomerControllerImpl::class.java)
            .`in`(Scopes.SINGLETON)
        bind(Configuration::class.java).to(LocalConfiguration::class.java).`in`(Scopes.SINGLETON)
        bind(Specification::class.java).to(AdminSpecification::class.java).`in`(Scopes.SINGLETON)
        bind(BookstoreController::class.java).to(BookstoreControllerImpl::class.java)
            .`in`(Scopes.SINGLETON)
        bind(BookstoreProductController::class.java).to(BookstoreProductControllerImpl::class.java)
            .`in`(Scopes.SINGLETON)
    }

}