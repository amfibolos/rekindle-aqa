package com.rekindle.book.store.domain.valueobjects

import java.math.BigDecimal

data class Item(
    var productId: String,
    var quantity: Int,
    var price: BigDecimal,
    var subTotal: BigDecimal
) : ValueObject
