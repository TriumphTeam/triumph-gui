package dev.triumphteam.gui;

import dev.triumphteam.gui.guis.BaseGui;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class TriumphGui {

    // The plugin instance for registering the event and for the close delay.
    private static Plugin PLUGIN = null;

    private TriumphGui() {}

    public static void init(final @NotNull Plugin plugin) {
        PLUGIN = plugin;
    }

    public static @NotNull Plugin getPlugin() {
        if (PLUGIN == null) init(JavaPlugin.getProvidingPlugin(BaseGui.class));
        return PLUGIN;
    }
}
