plugins {
    id("gui.base")
    id("gui.library")
}

dependencies {
    compileOnlyApi(libs.guava)
    compileOnlyApi(libs.adventure.api)

    compileOnlyApi(libs.logger)
}
