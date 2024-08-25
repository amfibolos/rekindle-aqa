plugins {
    id("rekindle.book.store.aqa.kotlin-library-conventions")
}
dependencies {
    implementation(project(":domain:core"))
    implementation(project(":domain:application"))
    implementation(libs.com.google.guice)
    api(libs.io.rest.assured)
    //testImplementation("org.jetbrains.kotlin:kotlin-test")
}