rootProject.name = "triumph-gui"

listOf("core",).forEach {
    include(it)
    findProject(":$it")?.name = "triumph-gui-$it"
}