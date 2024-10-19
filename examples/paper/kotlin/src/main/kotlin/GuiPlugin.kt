package dev.triumphteam.gui.example

import dev.triumphteam.gui.example.examples.CookieClicker
import dev.triumphteam.gui.example.examples.Static
import dev.triumphteam.gui.example.examples.UpdatingTitle
import org.bukkit.plugin.java.JavaPlugin

public class GuiPlugin : JavaPlugin() {

    override fun onEnable() {
        getCommand("gui-static")?.setExecutor(Static())
        getCommand("gui-clicker")?.setExecutor(CookieClicker())
        getCommand("gui-title")?.setExecutor(UpdatingTitle())
    }
}
