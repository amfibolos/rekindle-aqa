package com.rekindle.book.store.aqa.di

import com.google.inject.Guice
import com.rekindle.book.store.domain.application.di.GuiceJunit5Extension

class AdminExtension : GuiceJunit5Extension(Guice.createInjector(AdminModule())) {

}