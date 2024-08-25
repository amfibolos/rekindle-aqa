package com.rekindle.book.store.domain.valueobjects

data class Order(
    var customerId: String,
    var bookstoreId: String,
    var items: List<Item>,
    var address: Address
) : ValueObject
