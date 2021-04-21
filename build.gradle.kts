plugins {
    id("java-library")
}

allprojects {

    apply {
        plugin("java-library")
    }

    group = "dev.triumphteam"
    version = "3.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/groups/public/")
        maven("https://papermc.io/repo/repository/maven-public/")
    }

    dependencies {
        compileOnly("org.jetbrains:annotations:20.1.0")
        compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    }

    tasks {
        withType<JavaCompile> {
            sourceCompatibility = "1.8"
            targetCompatibility = "1.8"
            options.encoding = "UTF-8"
        }
    }

}
