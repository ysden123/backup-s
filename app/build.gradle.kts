/*
 * Copyright (c) 2022. StulSoft
 */
plugins {
    // Apply the scala Plugin to add support for Scala.
    scala

    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

version = "2.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala-library:2.13.10")
    implementation("com.typesafe:config:1.4.2")
    implementation("commons-io:commons-io:2.11.0")
    implementation("org.apache.commons:commons-lang3:3.12.0")
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use JUnit4 test framework
            useJUnit("4.13.2")

            dependencies {
                // Use Scalatest for testing our library
                implementation("org.scalatest:scalatest_2.13:3.2.13")
                implementation("org.scalatestplus:junit-4-13_2.13:3.2.2.0")

                // Need scala-xml at test runtime
                runtimeOnly("org.scala-lang.modules:scala-xml_2.13:1.2.0")
            }
        }
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass.set("com.stulsoft.backup.s.App")
    applicationDefaultJvmArgs = listOf("-Dconfig.file=application.conf")
}

tasks.named("startScripts"){
    doLast {
        layout.buildDirectory.file("scripts/app.bat")
            .get()
            .asFile
            .appendText("pause")
    }
}