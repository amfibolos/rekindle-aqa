package com.rekindle.book.store.domain.filters

import com.fasterxml.jackson.databind.ser.FilterProvider
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider

class JacksonFilters {
    companion object {
        val creditFilter: FilterProvider = SimpleFilterProvider().addFilter(
            Filter.CREDIT_FILTER,
            SimpleBeanPropertyFilter.serializeAllExcept("id", "transactionType")
        )
        val customerFilter: FilterProvider = SimpleFilterProvider().addFilter(
            Filter.CUSTOMER_FILTER,
            SimpleBeanPropertyFilter.serializeAllExcept("id")
        )
        val bookstoreFilter: FilterProvider = SimpleFilterProvider().addFilter(
            Filter.BOOKSTORE_FILTER,
            SimpleBeanPropertyFilter.serializeAllExcept("id")
        )
        val bookstoreProductFilter: FilterProvider = SimpleFilterProvider().addFilter(
            Filter.BOOKSTORE_PRODUCT_FILTER,
            SimpleBeanPropertyFilter.serializeAllExcept("id", "bookstoreId", "bookstores")
        )
    }
}