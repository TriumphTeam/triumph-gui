plugins {
    id("gui.base")
}

dependencies {
    compileOnlyApi(libs.guava)
    compileOnlyApi(libs.adventure.api)

    compileOnlyApi(libs.logger)
}
