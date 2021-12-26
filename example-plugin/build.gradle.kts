plugins {
    id("com.github.johnrengelman.shadow") version "7.1.1"
    id("xyz.jpenilla.run-paper") version "1.0.6"
}

dependencies {
    implementation(project(":triumph-gui"))
}

tasks {
    shadowJar {
        archiveClassifier.set("")
    }

    build {
        dependsOn("shadowJar")
    }

    runServer {
        minecraftVersion("1.18.1")
    }
}