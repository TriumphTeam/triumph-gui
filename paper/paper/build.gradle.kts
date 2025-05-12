plugins {
    id("gui.paper")
    id("gui.library")
}

dependencies {
    api(projects.triumphGuiCore)
    api(projects.triumphGuiPaperNmsCommon)

    compileOnly(libs.mojang.auth)
}
