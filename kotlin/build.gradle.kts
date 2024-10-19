plugins {
    id("gui.base")
    id("gui.library")
}

dependencies {
    api(libs.nova.kotlin)
    api(projects.triumphGuiCore)

    compileOnly(kotlin("stdlib"))
    compileOnly(libs.adventure.api)
}
