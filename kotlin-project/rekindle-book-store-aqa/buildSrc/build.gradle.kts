plugins {
    `kotlin-dsl`
    id("io.qameta.allure") version "2.11.2" apply false
}

repositories {
    gradlePluginPortal()
}

dependencies {
    implementation(libs.kotlin.gradle.plugin)
    implementation("io.qameta.allure.gradle.allure:allure-plugin:2.11.2")
}
