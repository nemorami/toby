import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("org.jetbrains.dokka") version "1.6.10"

}

group = "me.nemorami"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    jcenter()
}

dependencies {
    implementation("org.springframework:spring-core:+")
    implementation("org.springframework:spring-context:+")
    implementation("org.springframework:spring-beans:+")
    implementation("org.springframework:spring-expression:+")
    implementation("org.postgresql:postgresql:+")
    implementation("org.springframework:spring-jdbc:+")
    implementation("javax.mail:com.springsource.javax.mail:+")


    testImplementation(kotlin("test-junit5"))
    testImplementation("org.springframework:spring-test:+")
    testImplementation("org.junit.jupiter:junit-jupiter-api:+")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:+")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}