package dev.triumphteam.gui.example;

import org.bukkit.plugin.java.JavaPlugin;

public final class GuiPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("example").setExecutor(this);
    }
}
