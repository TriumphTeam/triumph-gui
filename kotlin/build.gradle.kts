plugins {
    id("gui.base")
    id("gui.library")
}

dependencies {
    api(libs.nova.kotlin)
    implementation(kotlin("stdlib"))
    implementation(projects.triumphGuiCore)
}
