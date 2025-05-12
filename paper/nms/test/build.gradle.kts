plugins {
    id("gui.paper")
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.16"
}

dependencies {
    api(projects.triumphGuiPaperNmsCommon)
    paperweight.paperDevBundle("1.21.4-R0.1-SNAPSHOT")
}

tasks.assemble {
    dependsOn(tasks.reobfJar)
}
