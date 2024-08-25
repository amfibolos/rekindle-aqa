package com.rekindle.book.store.domain.valueobjects

import com.fasterxml.jackson.annotation.JsonFilter
import com.fasterxml.jackson.annotation.JsonProperty
import com.rekindle.book.store.domain.filters.Filter

@JsonFilter(Filter.BOOKSTORE_FILTER)
data class Bookstore(
    var id: String? = "",
    var name: String?,
    @get:JsonProperty("isActive") var isActive: Boolean?
) : ValueObject
