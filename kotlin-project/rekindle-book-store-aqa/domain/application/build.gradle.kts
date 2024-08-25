plugins {
    id("rekindle.book.store.aqa.kotlin-library-conventions")
}
dependencies {
    implementation(project(":domain:core"))
    api(libs.com.google.guice)
    //testImplementation("org.jetbrains.kotlin:kotlin-test")
}