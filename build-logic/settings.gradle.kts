import dev.triumphteam.root.localLibs
import dev.triumphteam.root.releasesRepo

rootProject.name = "build-logic"

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        gradlePluginPortal()
        mavenCentral()
        releasesRepo()
    }

    versionCatalogs {

        register("libs") {
            from(files(localLibs))
        }
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.triumphteam.dev/releases")
    }
}

plugins {
    id("dev.triumphteam.root.settings") version "0.0.14"
}
