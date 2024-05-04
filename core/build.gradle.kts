plugins {
    id("gui.base")
}

dependencies {
    implementation(libs.caffeine)
    compileOnly("org.slf4j:slf4j-api:2.0.13")
}
