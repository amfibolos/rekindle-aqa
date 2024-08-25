package com.rekindle.book.store.domain.application.di

import com.google.inject.Injector
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver

open class GuiceJunit5Extension(private val injector: Injector) : ParameterResolver {
    override fun supportsParameter(p0: ParameterContext?, p1: ExtensionContext?): Boolean {
        return injector.getBinding(p0!!.parameter.type) != null
    }

    override fun resolveParameter(p0: ParameterContext?, p1: ExtensionContext?): Any {
        return injector.getInstance(p0!!.parameter.getType());
    }
}