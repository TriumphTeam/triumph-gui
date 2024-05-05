plugins {
    id("gui.base")
}

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    api(projects.triumphGuiCore)
    compileOnly(libs.paper)
}
