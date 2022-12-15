/*
 * Copyright (c) 2022. StulSoft
 */
plugins {
    scala
    application
}

version = "2.2.0"

repositories {
    mavenCentral()
}

val json4sVersion = "4.1.0-M2"

dependencies {
    implementation("org.scala-lang:scala-library:2.13.10")
    implementation("commons-io:commons-io:2.11.0")
    implementation("org.apache.commons:commons-lang3:3.12.0")

    implementation("org.json4s:json4s-native_2.13:$json4sVersion")
    implementation("org.json4s:json4s-jackson_2.13:$json4sVersion")

    testImplementation("org.scalatest:scalatest_2.13:3.3.0-SNAP3")
    testImplementation("org.scalatestplus:junit-4-12_2.13:3.3.0.0-SNAP2")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

application {
    mainClass.set("com.stulsoft.backup.s.App")
    applicationDefaultJvmArgs = listOf("-Drelease")
}

tasks.named("startScripts"){
    doLast {
        layout.buildDirectory.file("scripts/app.bat")
            .get()
            .asFile
            .appendText("pause")
    }
}