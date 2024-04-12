plugins {
    `kotlin-dsl`
}

dependencies {
    // Hack to allow version catalog inside convention plugins
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    // Bundled plugins
    implementation(libs.bundles.build)
}
