package dev.triumphteam.gui.paper;

import dev.triumphteam.gui.exception.TriumphGuiException;
import dev.triumphteam.gui.settings.GuiSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class PaperGuiSettings extends GuiSettings<Player, ItemStack, PaperGuiSettings> {

    private static final PaperGuiSettings INSTANCE = new PaperGuiSettings();

    private boolean listenerRegistered = false;
    private Plugin plugin = null;

    private PaperGuiSettings() {}

    public static PaperGuiSettings get() {
        return INSTANCE;
    }

    public PaperGuiSettings register(final @NotNull Plugin plugin) {
        // Only register listener once
        if (this.listenerRegistered) return this;

        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(new GuiBukkitListener(), plugin);
        this.listenerRegistered = true;
        return this;
    }

    public @NotNull Plugin getPlugin() {
        if (plugin == null) {
            throw new TriumphGuiException("An error occurred while attempting to get the plugin instance.");
        }

        return plugin;
    }
}
