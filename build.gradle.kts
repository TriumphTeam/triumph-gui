plugins {
    id("java-library")
    id("com.github.hierynomus.license") version "0.16.1"
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

subprojects {

    apply {
        plugin("java-library")
        plugin("com.github.hierynomus.license")
    }

    group = "dev.triumphteam"
    version = "3.1.7"

    dependencies {
        compileOnly("org.jetbrains:annotations:21.0.1")
        compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")

        val adventureVersion = "4.14.0"
        api("net.kyori:adventure-api:$adventureVersion")
        api("net.kyori:adventure-text-serializer-legacy:$adventureVersion")
        api("net.kyori:adventure-text-serializer-gson:$adventureVersion")
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
