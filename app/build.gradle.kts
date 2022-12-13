/*
 * Copyright (c) 2022. StulSoft
 */
plugins {
    scala
    application
}

version = "2.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala-library:2.13.10")
    implementation("com.typesafe:config:1.4.2")
    implementation("commons-io:commons-io:2.11.0")
    implementation("org.apache.commons:commons-lang3:3.12.0")

    testImplementation("org.scalatest:scalatest_2.13:3.3.0-SNAP3")
    testImplementation("org.scalatestplus:junit-4-12_2.13:3.3.0.0-SNAP2")
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