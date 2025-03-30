plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.0"
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "lovemetal"
version = ""

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.telegram:telegrambots-longpolling:7.10.0")
    implementation("org.telegram:telegrambots-client:7.10.0")
    implementation("org.slf4j:slf4j-simple:2.0.13")
    implementation("org.telegram:telegrambots-spring-boot-starter:6.9.7.1")
    implementation("org.telegram:telegrambots:6.5.0")

    compileOnly("org.projectlombok:lombok:1.18.36")
    annotationProcessor("org.projectlombok:lombok:1.18.36")

    implementation("org.slf4j:slf4j-api:2.0.9")
    implementation("org.slf4j:slf4j-simple:2.0.9")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "lovemetal.Main"
        }
    }

    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("")
    }
}