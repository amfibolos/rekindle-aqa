package com.rekindle.book.store.domain.valueobjects

import com.fasterxml.jackson.annotation.JsonFilter
import com.rekindle.book.store.domain.filters.Filter

@JsonFilter(Filter.CUSTOMER_FILTER)
data class Customer(
    var id: String? = "",
    var username: String?,
    var firstName: String?,
    var lastName: String?
) : ValueObject
