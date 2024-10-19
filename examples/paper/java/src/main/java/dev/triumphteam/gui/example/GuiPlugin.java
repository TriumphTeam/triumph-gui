package dev.triumphteam.gui.example;

import dev.triumphteam.gui.example.examples.CookieClicker;
import dev.triumphteam.gui.example.examples.Static;
import dev.triumphteam.gui.example.examples.UpdatingTitle;
import org.bukkit.plugin.java.JavaPlugin;

public final class GuiPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("gui-static").setExecutor(new Static());
        getCommand("gui-clicker").setExecutor(new CookieClicker());
        getCommand("gui-title").setExecutor(new UpdatingTitle());
    }
}
