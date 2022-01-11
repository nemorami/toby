import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.0"
    id("org.jetbrains.dokka") version "1.6.0"

}

group = "me.nemorami"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.springframework:spring-core")
    implementation("org.springframework:spring-context:5.3.3")
    implementation("org.springframework:spring-beans:5.3.3")
    implementation("org.springframework:spring-expression:5.3.3")
    implementation("org.postgresql:postgresql:42.2.18")
    implementation("org.springframework:spring-jdbc:5.3.3")

    testImplementation(kotlin("test-junit5"))
    testImplementation("org.springframework:spring-test:5.3.3")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}