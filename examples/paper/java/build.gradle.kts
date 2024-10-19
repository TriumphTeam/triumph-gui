import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

plugins {
    id("gui.base")
    id("gui.paper-example")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(projects.triumphGuiPaper)
}

tasks {
    runServer {
        minecraftVersion("1.21.1")
    }

    bukkitPluginYaml {
        main.set("dev.triumphteam.gui.example.GuiPlugin")
        load = BukkitPluginYaml.PluginLoadOrder.STARTUP
        authors.add("Matt")
        apiVersion = "1.21"
        commands.add(commands.create("example"))
        foliaSupported.set(true)
    }
}
