import dev.triumphteam.root.root

plugins {
    `kotlin-dsl`
    id("dev.triumphteam.root.logic") version "0.0.31"
}

dependencies {
    // Hack to allow version catalog inside convention plugins
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    // Bundled plugins
    implementation(libs.bundles.build)

    // Bundled example plugins
    implementation(libs.bundles.paper.examples)

    root("0.0.31")
}
