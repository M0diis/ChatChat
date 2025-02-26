import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("chatchat.base-conventions")
    id("chatchat.publish-conventions")
    id("com.gradleup.shadow") version "8.3.5"
}

repositories {
    // adventure snapshot repo
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")

    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    api(libs.adventure.bukkit)
    api(libs.adventure.minimessage)
    api(libs.adventure.configurate)

    compileOnly("io.papermc.paper:paper-api:1.21.3-R0.1-SNAPSHOT")
}

tasks {
    withType<ShadowJar> {
        listOf(
            "io.leangen",
        ).forEach { relocate(it, "at.helpch.chatchat.libs.$it") }

        archiveFileName.set("ChatChat-API-${project.version}.jar")
    }
}
