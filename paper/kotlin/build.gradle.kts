plugins {
    id("gui.base")
    id("gui.library")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly(libs.paper)
    compileOnly(kotlin("stdlib"))

    api(projects.triumphGuiPaper)
    api(projects.triumphGuiKotlin)
}
