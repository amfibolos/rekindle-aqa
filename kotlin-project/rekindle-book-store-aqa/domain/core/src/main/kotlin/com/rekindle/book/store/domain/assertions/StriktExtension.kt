package com.rekindle.book.store.domain.assertions

import strikt.api.Assertion

fun <T> Assertion.Builder<T>.isEqualToEither(
    expected: T?,
    expectedOther: T?
): Assertion.Builder<T> =
    assert("is equal to %s", expected) {
        when (it) {
            expected -> pass(actual = it)
            expectedOther -> pass(actual = it)
            is ByteArray -> if ((expected is ByteArray && it.contentEquals(expected)) ||
                (expectedOther is ByteArray && it.contentEquals(expectedOther))
            ) pass(actual = it) else fail(
                actual = it
            )

            is BooleanArray -> if ((expected is BooleanArray && it.contentEquals(expected)) ||
                (expectedOther is BooleanArray && it.contentEquals(expectedOther))
            ) pass(
                actual = it
            ) else fail(actual = it)

            is CharArray -> if ((expected is CharArray && it.contentEquals(expected)) ||
                (expectedOther is CharArray && it.contentEquals(expectedOther))
            ) pass(actual = it) else fail(
                actual = it
            )

            is ShortArray -> if ((expected is ShortArray && it.contentEquals(expected)) ||
                (expectedOther is ShortArray && it.contentEquals(expectedOther))
            ) pass(actual = it) else fail(
                actual = it
            )

            is IntArray -> if ((expected is IntArray && it.contentEquals(expected)) ||
                (expectedOther is IntArray && it.contentEquals(expectedOther))
            ) pass(actual = it) else fail(
                actual = it
            )

            is LongArray -> if ((expected is LongArray && it.contentEquals(expected)) ||
                (expectedOther is LongArray && it.contentEquals(expectedOther))
            ) pass(actual = it) else fail(
                actual = it
            )

            is FloatArray -> if ((expected is FloatArray && it.contentEquals(expected)) ||
                (expectedOther is FloatArray && it.contentEquals(expectedOther))
            ) pass(actual = it) else fail(
                actual = it
            )

            is DoubleArray -> if ((expected is DoubleArray && it.contentEquals(expected)) ||
                (expectedOther is DoubleArray && it.contentEquals(expectedOther))
            ) pass(actual = it) else fail(
                actual = it
            )

            is Array<*> -> if ((expected is Array<*> && it.contentEquals(expected)) ||
                (expectedOther is Array<*> && it.contentEquals(expectedOther))
            ) pass(actual = it) else fail(
                actual = it
            )

            else -> fail(actual = it)
        }
    }