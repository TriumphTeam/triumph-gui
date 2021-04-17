repositories {
    mavenCentral()
    maven("https://repo.mattstudios.me/artifactory/public/")
    maven("https://libraries.minecraft.net/")
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    api(project(":triumph-gui-core"))

    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    compileOnly("org.jetbrains:annotations:20.0.0")
}