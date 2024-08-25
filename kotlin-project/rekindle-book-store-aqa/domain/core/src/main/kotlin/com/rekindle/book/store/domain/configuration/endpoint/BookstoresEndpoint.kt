package com.rekindle.book.store.domain.configuration.endpoint

import org.aeonbits.owner.Config
import org.aeonbits.owner.Config.Key
import org.aeonbits.owner.Config.Sources

@Sources(value = ["classpath:endpoints/bookstores-endpoints.properties"])
interface BookstoresEndpoint : Config {

    @Key("bookstores")
    fun bookstores(): String

    @Key("bookstores.by.id")
    fun bookstoreById(): String

    @Key("bookstore.product.by.store.id")
    fun bookstoreProductByStoreId(): String

    @Key("bookstores.product.by.id")
    fun bookstoreProductById(): String

    @Key("bookstores.products")
    fun bookstoreProducts(): String

    @Key("bookstore.product.by.store.id.product.id")
    fun bookstoreProductByStoreIdProductId(): String

}