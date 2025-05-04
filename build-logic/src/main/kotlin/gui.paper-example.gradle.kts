import org.gradle.accessors.dm.LibrariesForLibs

// Hack which exposes `libs` to this convention plugin
val libs = the<LibrariesForLibs>()

plugins {
    `java-library`
    id("xyz.jpenilla.run-paper")
    id("xyz.jpenilla.resource-factory-bukkit-convention")
    id("com.gradleup.shadow")
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.paper)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks {
    runServer {
        minecraftVersion("1.21.4")
    }
}
