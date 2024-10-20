import dev.triumphteam.root.includeProject

dependencyResolutionManagement {
    includeBuild("build-logic")
    repositories.gradlePluginPortal()
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.triumphteam.dev/releases")
    }
}

rootProject.name = "triumph-gui"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

plugins {
    id("dev.triumphteam.root.settings") version "0.0.14"
}

listOf(
    "core" to "core",
    "kotlin" to "kotlin",

    // Platforms and their Kotlin versions
    "paper/paper" to "paper",
    "paper/kotlin" to "paper-kotlin",

    // Example projects
    "examples/paper/java" to "example-paper-java",
    "examples/paper/kotlin" to "example-paper-kotlin",
).forEach(::includeProject)
