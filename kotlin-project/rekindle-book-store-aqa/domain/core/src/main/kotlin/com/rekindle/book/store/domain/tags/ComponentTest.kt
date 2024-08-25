package com.rekindle.book.store.domain.tags

import org.junit.jupiter.api.Tag

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.TYPE
)
@Retention(
    AnnotationRetention.RUNTIME
)
@Tag("component")
annotation class ComponentTest
