plugins {
    id("rekindle.book.store.aqa.kotlin-library-conventions")
}
dependencies {
    api(libs.org.aeonbits)
    implementation(libs.io.kotlin.faker)
    implementation(libs.net.serenity.bdd.core)
    implementation(libs.com.google.guice)
    api(libs.com.jackson.databind)
    api(libs.com.jackson.core)
    api(libs.com.jackson.annotations)
    api(libs.com.jackson.kotlin)
    //testImplementation("org.jetbrains.kotlin:kotlin-test")
}