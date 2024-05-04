dependencyResolutionManagement {
    includeBuild("build-logic")
    repositories.gradlePluginPortal()
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "triumph-gui"

listOf("core", "paper").forEach(::includeProject)

fun includeProject(name: String) {
    include(name) {
        this.name = "${rootProject.name}-$name"
    }
}

fun include(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}

//include("fabric-test")
include("test-plugin")
