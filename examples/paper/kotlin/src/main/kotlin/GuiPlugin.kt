package dev.triumphteam.gui.example

import org.bukkit.plugin.java.JavaPlugin

public class GuiPlugin : JavaPlugin() {

    override fun onEnable() {
        getCommand("example")?.setExecutor(this)
    }
}
