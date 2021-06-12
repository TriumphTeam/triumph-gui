plugins {
    id("maven-publish")
}

repositories {
    mavenCentral()
    maven("https://repo.mattstudios.me/artifactory/public/")
    maven("https://libraries.minecraft.net/")
}

dependencies {
    compileOnly("com.mojang:authlib:1.5.21")
}

val javaComponent: SoftwareComponent = components["java"]

tasks {

    val sourcesJar by creating(Jar::class) {
        archiveClassifier.set("sources")
        from(sourceSets.main.get().allSource)
    }

    val javadocJar by creating(Jar::class) {
        dependsOn.add(javadoc)
        archiveClassifier.set("javadoc")
        from(javadoc)
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                from(javaComponent)
                artifact(sourcesJar)
                artifact(javadocJar)
                versionMapping {
                    usage("java-api") {
                        fromResolutionOf("runtimeClasspath")
                    }
                    usage("java-runtime") {
                        fromResolutionResult()
                    }
                }
                pom {
                    name.set("Triumph GUI")
                    description.set("Lib for easy creation of GUIs for Bukkit plugins.")
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
                        }
                    }

                    scm {
                        connection.set("scm:git:git://github.com/TriumphTeam/triumph-gui.git")
                        developerConnection.set("scm:git:ssh://github.com:TriumphTeam/triumph-gui.git")
                        url.set("https://github.com/TriumphTeam/triumph-gui")
                    }
                }
            }
        }

        repositories {
            maven {
                credentials {
                    username = System.getenv("REPO_USER")
                    password = System.getenv("REPO_PASS")
                }

                url = uri("https://repo.mattstudios.me/artifactory/public")
            }
        }

    }

}