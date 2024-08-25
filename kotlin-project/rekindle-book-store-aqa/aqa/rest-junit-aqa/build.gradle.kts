plugins {
    id("rekindle.book.store.aqa.kotlin-library-conventions")
}
dependencies{
    testImplementation(project(":domain:core"))
    testImplementation(project(":domain:application"))
    testImplementation(project(":rest:rest-assured-adapter"))
}