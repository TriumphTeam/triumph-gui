plugins {
    id("gui.paper")
    id("gui.library")
}

dependencies {
    api(projects.triumphGuiCore)
    api(projects.triumphGuiPaperNmsCommon)

    api(projects.triumphGuiPaperNmsV121)

    compileOnly(libs.mojang.auth)
}

