plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

rootProject.name = "rekindle-book-store-aqa"
include("domain")
include("domain:core")
findProject(":domain:core")?.name = "core"
include("domain:application")
findProject(":domain:application")?.name = "application"
include("rest")
include("rest:rest-assured-adapter")
findProject(":rest:rest-assured-adapter")?.name = "rest-assured-adapter"
include("rest:serenity-screenplay-rest-adapter")
findProject(":rest:serenity-screenplay-rest-adapter")?.name = "serenity-screenplay-rest-adapter"
include("aqa")
include("aqa:rest-junit-aqa")
findProject(":aqa:rest-junit-aqa")?.name = "rest-junit-aqa"
