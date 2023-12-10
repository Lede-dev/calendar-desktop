import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.github.johnrengelman.shadow") version "4.0.4"
}

group = "net.ledestudio"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    commonMainImplementation("com.google.code.gson:gson:2.10.1")

    // https://mvnrepository.com/artifact/org.jetbrains.compose.material3/material3-desktop
    commonMainImplementation("org.jetbrains.compose.material3:material3-desktop:1.5.11")

    // https://mvnrepository.com/artifact/org.jetbrains.skiko/skiko-awt-runtime-windows-x64
    commonMainImplementation("org.jetbrains.skiko:skiko-awt-runtime-windows-x64:0.7.77")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "calendar"
            packageVersion = "1.0.0"
        }
    }
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set("shadow")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "net.ledestudio.calendar.CalendarDesktopKt"))
        }
    }
}


