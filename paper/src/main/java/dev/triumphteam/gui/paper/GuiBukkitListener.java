package dev.triumphteam.gui.paper;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class GuiBukkitListener implements Listener {

    private static final Plugin plugin = JavaPlugin.getProvidingPlugin(GuiBukkitListener.class);

    public static void register() {
        Bukkit.getServer().getPluginManager().registerEvents(new GuiBukkitListener(), plugin);
    }

    @EventHandler
    public void onGuiClick(final InventoryClickEvent event) {
        final var holder = event.getInventory().getHolder();
        if (!(holder instanceof final BukkitGuiView view)) {
            return;
        }

        event.setCancelled(true);

        view.getClickProcessor().processClick(event.getSlot(), view);
    }
}
