val bootJar: org.springframework.boot.gradle.tasks.bundling.BootJar by tasks

bootJar.enabled = false

plugins {
    java
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
}

group = "me.whitewin"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "org.springframework.boot")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-data-redis")
        implementation("org.springframework.boot:spring-boot-starter-data-jpa")
        runtimeOnly("com.h2database:h2")
        runtimeOnly("com.mysql:mysql-connector-j")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.springframework.boot:spring-boot-starter")
        testImplementation("org.springframework.boot:spring-boot-starter-test")

        implementation("org.springframework.boot:spring-boot-starter-validation")


        // jackson
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
        implementation("com.fasterxml.jackson.core:jackson-databind")

        // jpa
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
        annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
        annotationProcessor("jakarta.annotation:jakarta.annotation-api")
        annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    }
}


tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.jar {
    enabled = false
}