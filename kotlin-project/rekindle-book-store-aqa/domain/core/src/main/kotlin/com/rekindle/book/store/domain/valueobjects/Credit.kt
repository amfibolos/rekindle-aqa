package com.rekindle.book.store.domain.valueobjects

import com.fasterxml.jackson.annotation.JsonFilter
import com.rekindle.book.store.domain.filters.Filter
import java.math.BigDecimal

@JsonFilter(Filter.CREDIT_FILTER)
data class Credit(
    var id: String?,
    var customerId: String?,
    var totalPrice: BigDecimal?,
    var transactionType: TransactionType?
) : ValueObject