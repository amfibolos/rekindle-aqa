package com.rekindle.book.store.domain.valueobjects

import com.fasterxml.jackson.annotation.JsonFilter
import com.rekindle.book.store.domain.filters.Filter
import java.math.BigDecimal

@JsonFilter(Filter.BOOKSTORE_PRODUCT_FILTER)
data class Product(
    var id: String? = "",
    var name: String?,
    var price: BigDecimal?,
    var available: Boolean?,
    var bookstoreId: String? = "",
    var bookstores: List<String>? = listOf()
) : ValueObject
