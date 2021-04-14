rootProject.name = "triumph-gui"

listOf("gui").forEach {
    include(it)
    findProject(":$it")?.name = "triumph-$it"
}
