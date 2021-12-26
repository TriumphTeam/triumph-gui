rootProject.name = "triumph-gui"

include("example-plugin")
include("core")
findProject(":core")?.name = "triumph-gui"

listOf("kotlin").forEach {
    include(it)
    findProject(":$it")?.name = "triumph-gui-$it"
}