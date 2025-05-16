import dev.triumphteam.root.projects

dependencyResolutionManagement {
    includeBuild("build-logic")
    repositories.gradlePluginPortal()
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.triumphteam.dev/releases")
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

rootProject.name = "triumph-gui"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

plugins {
    id("dev.triumphteam.root.settings") version "0.0.31"
}

projects {
    single(id = "core")
    single(id = "kotlin")

    // Platforms and their Kotlin versions

    group(namespace = "paper") {
        single(id = "paper")
        single(id = "kotlin", includeNamespace = true)

        // Nms projects
        group(namespace = "nms") {
            single(id = "common", includeNamespace = true)

            // NMS versions
            single(id = "v1_21", includeNamespace = true)
        }
    }

    // Examples

    group(namespace = "examples") {
        group("paper") {
            single(id = "java", includeNamespace = true)
            single(id = "kotlin", includeNamespace = true)
        }
    }
}
