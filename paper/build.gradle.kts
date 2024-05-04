plugins {
    id("gui.base")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    api(projects.triumphGuiCore)
    compileOnly(libs.paper)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}
