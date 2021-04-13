allprojects {

    apply {
        plugin("java-library")
    }

    group = "me.mattstudios"
    version = "3.0.5-SNAPSHOT"

    repositories {
        mavenCentral()
        maven("https://hub.spigotmc.org/nexus/content/groups/public/")
    }

    tasks {
        withType<JavaCompile> {
            sourceCompatibility = "1.8"
            targetCompatibility = "1.8"
            options.encoding = "UTF-8"
        }
    }

}
