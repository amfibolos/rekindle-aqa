package com.rekindle.book.store.domain.valueobjects

import com.fasterxml.jackson.databind.ser.FilterProvider
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

interface ValueObject {
    fun toJsonString(): String {
        return jacksonObjectMapper().writeValueAsString(this)
    }

    fun toJsonStringFiltered(filter: FilterProvider): String {
        return jacksonObjectMapper().setFilterProvider(filter).writeValueAsString(this)
    }
}