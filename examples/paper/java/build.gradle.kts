import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

plugins {
    id("gui.base")
    id("gui.paper-example")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.codemc.io/repository/maven-releases/")
    maven("https://repo.codemc.io/repository/maven-snapshots/")
}

dependencies {
    implementation(projects.triumphGuiPaper)
    compileOnly("com.github.retrooper:packetevents-spigot:2.7.0")
}

bukkitPluginYaml {
    main.set("dev.triumphteam.gui.example.GuiPlugin")
    load = BukkitPluginYaml.PluginLoadOrder.STARTUP
    authors.add("Matt")
    apiVersion = "1.21"
    commands.add(commands.create("gui-static"))
    commands.add(commands.create("gui-clicker"))
    commands.add(commands.create("gui-title"))
    foliaSupported.set(true)
}
