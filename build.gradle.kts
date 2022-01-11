plugins {
    id("java-library")
    id("com.github.hierynomus.license") version "0.16.1"
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    }
}

subprojects {

    apply {
        plugin("java-library")
        plugin("com.github.hierynomus.license")
    }

    group = "dev.triumphteam"
    version = "3.1.0"

    dependencies {
        compileOnly("org.jetbrains:annotations:21.0.1")
        compileOnly("org.spigotmc:spigot-api:1.18.1-R0.1-SNAPSHOT")
        api("net.kyori:adventure-api:4.9.2")
        api("net.kyori:adventure-text-serializer-legacy:4.9.2")
        api("net.kyori:adventure-text-serializer-gson:4.9.2")
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
