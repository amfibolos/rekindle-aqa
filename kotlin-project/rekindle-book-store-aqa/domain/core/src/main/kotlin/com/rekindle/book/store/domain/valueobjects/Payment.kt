package com.rekindle.book.store.domain.valueobjects

import java.math.BigDecimal

data class Payment(var customerId: String?, var price: BigDecimal?, var status: PaymentStatus?) :
    ValueObject {
}