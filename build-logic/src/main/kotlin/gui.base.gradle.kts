import org.gradle.accessors.dm.LibrariesForLibs

// Hack which exposes `libs` to this convention plugin
val libs = the<LibrariesForLibs>()

plugins {
    `java-library`
    kotlin("jvm")
    id("dev.triumphteam.root")
    id("com.github.hierynomus.license")
}

repositories {
    mavenCentral()
    maven("https://repo.triumphteam.dev/snapshots/")
}

dependencies {
    compileOnly(libs.annotations)
}


java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    withSourcesJar()
    withJavadocJar()
}

root {
    configureKotlin {
        explicitApi()
        jvmVersion(21)
    }
}

license {
    header = rootProject.file("LICENSE")
    encoding = "UTF-8"
    useDefaultMappings = true

    include("**/*.kt")
    include("**/*.java")
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.add("-parameters")
    }
}
