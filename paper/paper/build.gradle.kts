plugins {
    id("gui.base")
    id("gui.library")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://libraries.minecraft.net/")
}

dependencies {
    api(projects.triumphGuiCore)
    compileOnly(libs.paper)
    compileOnly("com.mojang:authlib:1.5.25")
}
