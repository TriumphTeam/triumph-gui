plugins {
    `maven-publish`
    signing
    id("com.vanniktech.maven.publish") version "0.34.0"
}

repositories {
    mavenCentral()
    maven("https://libraries.minecraft.net/")
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.7-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:21.0.1")
    api(project(":triumph-gui")) {
        exclude(group = "net.kyori")
    }
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
    disableAutoTargetJvm()
}
mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

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
