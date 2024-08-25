package com.rekindle.book.store.domain.valueobjects

data class OrderTracker(var trackingId: String, var orderId: String) : ValueObject
