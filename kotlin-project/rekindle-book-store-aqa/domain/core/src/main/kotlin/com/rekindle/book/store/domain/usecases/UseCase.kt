package com.rekindle.book.store.domain.usecases

interface UseCase : Case {

    fun perform(): Unit
}