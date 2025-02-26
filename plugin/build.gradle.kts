import dev.triumphteam.helper.*
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("chatchat.base-conventions")
    id("com.gradleup.shadow") version "8.3.5"
    id("me.mattstudios.triumph") version "0.2.8"
}

version = "${rootProject.version}-${System.getenv("BUILD_NUMBER")}"

repositories {
    papi()
    triumphSnapshots()
    // adventure snapshot repo
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    // towny
    maven("https://repo.glaremasters.me/repository/towny/")
    // dsrv + dependencies
    maven("https://m2.dv8tion.net/releases")
    maven("https://nexus.scarsz.me/content/groups/public")
    // supervanish, griefprevention
    maven("https://jitpack.io")
    // essentialsx
    maven("https://repo.essentialsx.net/releases/")
    maven("https://repo.papermc.io/repository/maven-public/")

    flatDir {
        name = "libs.dir"
        dirs = setOf(file("libs"))
    }
}

dependencies {
    implementation(projects.chatChatApi)

    implementation(libs.triumph.cmds)
    implementation(libs.configurate)
    implementation(libs.bstats)

    compileOnly("io.papermc.paper:paper-api:1.21.3-R0.1-SNAPSHOT")
    compileOnly(libs.papi)
    compileOnly(libs.towny)
    compileOnly(libs.essentials)
    compileOnly(libs.discordsrv)
    compileOnly(libs.supervanish)
    compileOnly(libs.griefprevention)

    compileOnly(files("${rootDir}/libs/Authenticator.jar"))
}

tasks {
    withType<ShadowJar> {
        listOf("dev.triumphteam",
            "org.spongepowered",
            "io.leangen",
            "org.yaml",
            "org.bstats"
        ).forEach { relocate(it, "at.helpch.chatchat.libs.$it") }

        archiveFileName.set("ChatChat-${project.version}.jar")
    }
}
