package com.rekindle.book.store.domain.valueobjects

data class Address(var street: String, var postalCode: String, var city: String) : ValueObject {
}