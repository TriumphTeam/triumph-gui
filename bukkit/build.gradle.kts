plugins {
    id("gui.base")
}

repositories {
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    api(projects.triumphGuiCore)
    api(libs.spigot)
}
