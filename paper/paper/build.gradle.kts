plugins {
    id("gui.base")
    id("gui.library")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    api(projects.triumphGuiCore)
    compileOnly(libs.paper)
    compileOnly(libs.mojang.auth)
}
