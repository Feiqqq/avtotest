plugins {
    id("java")
    kotlin("jvm")
}

group = "ru.samokat.stocks"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.seleniumhq.selenium:selenium-java:4.12.1")
    testImplementation("io.github.bonigarcia:webdrivermanager:5.4.1")
    implementation("org.slf4j:slf4j-simple:2.0.9")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    maxParallelForks = 4
    useJUnitPlatform()

}
kotlin {
    jvmToolchain(17)
}