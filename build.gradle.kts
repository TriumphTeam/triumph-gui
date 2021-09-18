plugins {
    id("java-library")
    id("com.github.hierynomus.license") version "0.16.1"
}

allprojects {
    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/groups/public/")
        maven("https://papermc.io/repo/repository/maven-public/")
    }
}

subprojects {

    apply {
        plugin("java-library")
        plugin("com.github.hierynomus.license")
    }

    group = "dev.triumphteam"
    version = "3.0.3"

    dependencies {
        compileOnly("org.jetbrains:annotations:21.0.1")
        compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
        api("net.kyori:adventure-api:4.9.1")
        api("net.kyori:adventure-text-serializer-legacy:4.9.1")
    }

    license {
        header = rootProject.file("LICENSE")
        encoding = "UTF-8"
        mapping("java", "JAVADOC_STYLE")
        include("**/*.java")
    }

    tasks {
        withType<JavaCompile> {
            sourceCompatibility = "1.8"
            targetCompatibility = "1.8"
            options.encoding = "UTF-8"
        }
    }

}
