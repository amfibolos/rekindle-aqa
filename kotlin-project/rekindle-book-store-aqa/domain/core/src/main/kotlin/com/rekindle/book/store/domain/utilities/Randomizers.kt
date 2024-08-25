package com.rekindle.book.store.domain.utilities

import java.math.BigDecimal
import java.math.RoundingMode

fun randomPriceLessThanThousand(): BigDecimal {
    val min = BigDecimal.valueOf(1.0)
    val max = BigDecimal.valueOf(1000)
    val randomBigDecimal: BigDecimal =
        min.add(BigDecimal(Math.random()).multiply(max.subtract(min)))
    return randomBigDecimal.setScale(2, RoundingMode.HALF_EVEN)
}