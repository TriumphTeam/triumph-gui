plugins {
    id("gui.base")
    id("gui.library")
}

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    api(projects.triumphGuiCore)
    compileOnly(libs.paper)
}
