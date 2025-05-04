import xyz.jpenilla.resourcefactory.bukkit.BukkitPluginYaml

plugins {
    id("gui.base")
    id("gui.paper-example")
}

repositories {
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(projects.triumphGuiPaperKotlin)
}

bukkitPluginYaml {
    main.set("dev.triumphteam.gui.example.GuiPlugin")
    load = BukkitPluginYaml.PluginLoadOrder.STARTUP
    authors.add("Matt")
    apiVersion = "1.21"
    commands.add(commands.create("gui-static"))
    commands.add(commands.create("gui-clicker"))
    commands.add(commands.create("gui-title"))
    commands.add(commands.create("gui-page"))
    commands.add(commands.create("gui-scroll"))
    foliaSupported.set(true)
}
