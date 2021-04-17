rootProject.name = "triumph-gui"

listOf("core", "paper").forEach {
    include(it)
    findProject(":$it")?.name = "triumph-gui-$it"
}