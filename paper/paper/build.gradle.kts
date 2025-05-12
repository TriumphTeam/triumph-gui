plugins {
    id("gui.base")
    id("gui.library")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.16"
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-releases/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    api(projects.triumphGuiCore)
    compileOnly(libs.paper)
    compileOnly(libs.mojang.auth)
    compileOnly("com.github.retrooper:packetevents-spigot:2.7.0")

    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
}

tasks.assemble {
    dependsOn(tasks.reobfJar)
}
