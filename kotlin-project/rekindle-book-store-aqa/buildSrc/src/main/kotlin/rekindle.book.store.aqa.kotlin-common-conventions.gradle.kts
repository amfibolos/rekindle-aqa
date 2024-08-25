plugins {
    id("org.jetbrains.kotlin.jvm")
    id("io.qameta.allure")
}

repositories {
    mavenCentral()
}
group = "com.rekindle.book.store"
version = "1.0.0"
dependencies {
    implementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("io.strikt:strikt-core:0.34.1")
    implementation("io.strikt:strikt-jvm:0.34.1")
    testImplementation("io.qameta.allure:allure-junit5:2.25.0")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

allure {
    adapter {
        frameworks {
            junit5
        }
    }
}

val componentTest = task<Test>("component-test"){
    description = "Runs component tests."
    useJUnitPlatform{
        includeTags = setOf("component")
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform{
        includeTags = setOf("unit")
    }
}
