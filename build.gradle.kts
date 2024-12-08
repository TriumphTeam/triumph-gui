plugins {
    id("java-library")
    id("com.github.hierynomus.license") version "0.16.1"
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/groups/public/")
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

subprojects {

    apply {
        plugin("java-library")
        plugin("com.github.hierynomus.license")
    }

    group = "dev.triumphteam"
    version = "3.1.11"
}
