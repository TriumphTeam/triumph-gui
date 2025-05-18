plugins {
    id("gui.paper-weight")
}

dependencies {
    api(projects.triumphGuiPaperNmsCommon)
    paperweight.paperDevBundle("1.21.5-R0.1-SNAPSHOT")
}

tasks.assemble {
    dependsOn(tasks.reobfJar)
}
