/*
 * This file was generated by the Gradle 'init' task.
 */
val aliyunUsername : String by project
val aliyunPassword : String by project

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    id("org.jetbrains.kotlin.jvm")
    `java-library`
    `maven-publish`
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

group = projectGroup
version = projectVersion

publishing {
    publications {
        create<MavenPublication>("mcsLib") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            // change URLs to point to your repos, e.g. http://my.org/repo
            credentials {
                username = aliyunUsername
                password = aliyunPassword
            }
            val releasesRepoUrl = uri("https://packages.aliyun.com/maven/repository/2132034-release-rf0Mpj/")
            val snapshotsRepoUrl = uri("https://packages.aliyun.com/maven/repository/2132034-snapshot-5xXWTt/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
}

tasks.test {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
