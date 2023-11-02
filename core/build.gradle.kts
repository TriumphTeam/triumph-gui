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
                        username = System.getenv("REPO_USER")
                        password = System.getenv("REPO_PASS")
                    }

                    url = uri("https://repo.mattstudios.me/artifactory/public-snapshot/")
                    return@maven
                }

                credentials {
                    username = System.getenv("SONATYPE_USER")
                    password = System.getenv("SONATYPE_PASSWORD")
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

}
