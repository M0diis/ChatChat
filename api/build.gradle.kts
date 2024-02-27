import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("chatchat.base-conventions")
    id("chatchat.publish-conventions")
    id("com.github.johnrengelman.shadow") version "7.1.2"
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

    compileOnly("io.papermc.paper:paper-api:1.20.2-R0.1-SNAPSHOT")
}

tasks {
    withType<ShadowJar> {
        listOf(
            "io.leangen",
        ).forEach { relocate(it, "at.helpch.chatchat.libs.$it") }

        archiveFileName.set("ChatChat-API-${project.version}.jar")
    }
}
