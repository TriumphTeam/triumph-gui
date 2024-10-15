pluginManagement {
    plugins {
        kotlin("jvm") version "1.9.10"
    }
}
rootProject.name = "triumph-gui"

include("core")
findProject(":core")?.name = "triumph-gui"

listOf("kotlin").forEach {
    include(it)
    findProject(":$it")?.name = "triumph-gui-$it"
}

include("test-plugin")
