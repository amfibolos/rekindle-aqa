package com.rekindle.book.store.aqa.component

import com.google.inject.Inject
import com.rekindle.book.store.aqa.di.AdminExtension
import com.rekindle.book.store.domain.factory.FactoryKit
import com.rekindle.book.store.serenity.rest.controllers.payment.CreditController
import com.rekindle.book.store.serenity.rest.controllers.payment.PaymentController
import org.junit.jupiter.api.extension.ExtendWith
@ExtendWith(AdminExtension::class)
class PaymentCreditServiceComponentTest @Inject constructor(
    private val factoryKit: FactoryKit,
    private val paymentController: PaymentController,
    private val creditController: CreditController
) {
}