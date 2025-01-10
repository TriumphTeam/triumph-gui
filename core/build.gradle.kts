plugins {
    `maven-publish`
    signing
}

repositories {
    mavenCentral()
    maven("https://libraries.minecraft.net/")
}

dependencies {
    compileOnly("com.mojang:authlib:1.5.25")
    compileOnly("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")

    compileOnly("org.jetbrains:annotations:21.0.1")

    val adventureVersion = "4.17.0"
    api("net.kyori:adventure-api:$adventureVersion")
    api("net.kyori:adventure-text-serializer-legacy:$adventureVersion")
    api("net.kyori:adventure-text-serializer-gson:$adventureVersion")
    api("net.kyori:adventure-platform-bukkit:4.3.4")
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
        }

        repositories {
            maven {
                if (version.toString().contains("SNAPSHOT")) {
                    credentials {
                        username = project.providers.gradleProperty("triumph.repo.user").get()
                        password = project.providers.gradleProperty("triumph.repo.token").get()
                    }

                    url = uri("https://repo.triumphteam.dev/snapshots/")
                    return@maven
                }

                credentials {
                    username = project.providers.gradleProperty("ossrh.username").get()
                    password = project.providers.gradleProperty("ossrh.password").get()
                }

                url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            }
        }

    }

    signing {
        /*useGpgCmd()
        val signingKey = System.getenv("GPG_KEY")
        val signingPassword = System.getenv("GPG_PASS")
        val secretKey = System.getenv("GPG_SECRET_KEY")
        useInMemoryPgpKeys(signingKey, secretKey, signingPassword)*/
        sign(publishing.publications["maven"])
    }

    withType<JavaCompile> {
        sourceCompatibility = JavaVersion.VERSION_1_8.toString()
        targetCompatibility = JavaVersion.VERSION_1_8.toString()
        options.encoding = "UTF-8"
    }
}
