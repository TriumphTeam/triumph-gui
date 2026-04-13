plugins {
    `maven-publish`
    signing
    id("com.vanniktech.maven.publish") version "0.34.0"
}

repositories {
    mavenCentral()
    maven("https://libraries.minecraft.net/")
}

dependencies {
    compileOnly("com.mojang:authlib:1.5.25")
    compileOnly("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")

    compileOnly("org.jetbrains:annotations:21.0.1")

    val adventureVersion = "4.22.0"
    api("net.kyori:adventure-api:$adventureVersion")
    api("net.kyori:adventure-text-serializer-legacy:$adventureVersion")
    api("net.kyori:adventure-text-serializer-gson:$adventureVersion")
    api("net.kyori:adventure-platform-bukkit:4.4.1")
}

license {
    header = rootProject.file("LICENSE")
    encoding = "UTF-8"
    mapping("java", "JAVADOC_STYLE")
    include("**/*.java")
}

val javaComponent: SoftwareComponent = components["java"]

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

publishing {
    repositories {
        maven {
            name = "triumph"
            credentials {
                username = providers.gradleProperty("triumph.repo.user").get()
                password = providers.gradleProperty("triumph.repo.token").get()
            }

            url = uri("https://repo.triumphteam.dev/snapshots/")
        }

        // more repositories can go here
    }
}

mavenPublishing {
    // publishToMavenCentral()
    // signAllPublications()

    pom {
        name.set("Triumph GUI")
        description.set("Library for easy creation of GUIs for Bukkit plugins.")
        url.set("https://github.com/TriumphTeam/triumph-gui")
        licenses {
            license {
                name.set("MIT License")
                url.set("http://www.opensource.org/licenses/mit-license.php")
            }
        }
        developers {
            developer {
                id.set("matt")
                name.set("Mateus Moreira")
                organization.set("TriumphTeam")
                organizationUrl.set("https://github.com/TriumphTeam")
            }
        }
        scm {
            connection.set("scm:git:git://github.com/TriumphTeam/triumph-gui.git")
            developerConnection.set("scm:git:ssh://github.com:TriumphTeam/triumph-gui.git")
            url.set("https://github.com/TriumphTeam/triumph-gui")
        }
    }
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()
        options.encoding = "UTF-8"
    }
}
