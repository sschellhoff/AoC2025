plugins {
    kotlin("jvm") version "2.1.20"
}

group = "de.sschellhoff"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.ortools:ortools-java:9.8.3296")
    implementation("tools.aqua:z3-turnkey:4.14.1")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}