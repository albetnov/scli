import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "com.scli"
version = "1.1-dev"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
//    testImplementation("org.mockito:mockito-junit-jupiter:4.5.1")
    testImplementation("org.mockito:mockito-inline:4.6.0")
    testImplementation("com.github.stefanbirkner:system-lambda:1.2.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("shadow")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "com.example.MainKt"))
        }
        // @see https://youtrack.jetbrains.com/issue/KT-25709
        exclude("**/*.kotlin_metadata")
        exclude("**/*.kotlin_builtins")

        archiveClassifier.set("") // remove suffix `-all` as intellij can't find the library otherwise
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}