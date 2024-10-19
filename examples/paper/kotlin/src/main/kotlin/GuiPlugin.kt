package dev.triumphteam.gui.example

import dev.triumphteam.gui.example.examples.CookieClicker
import dev.triumphteam.gui.example.examples.StaticGui
import org.bukkit.plugin.java.JavaPlugin

public class GuiPlugin : JavaPlugin() {

    override fun onEnable() {
        getCommand("gui-static")?.setExecutor(StaticGui())
        getCommand("gui-clicker")?.setExecutor(CookieClicker())
    }
}
