plugins {
    id("gui.base")
    id("gui.library")
}

dependencies {
    api(libs.nova)

    compileOnlyApi(libs.guava)
    compileOnlyApi(libs.adventure.api)

    compileOnlyApi(libs.logger)
}
