plugins {
    id("gui.base")
    id("gui.library")
}

dependencies {
    api(libs.nova)

    compileOnly(libs.guava)
    compileOnly(libs.adventure.api)
    compileOnly(libs.logger)
}
