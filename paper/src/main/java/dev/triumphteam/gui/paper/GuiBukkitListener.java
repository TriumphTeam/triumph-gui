package dev.triumphteam.gui.paper;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class GuiBukkitListener implements Listener {

    public static void register() {
        // Auto-register listener if none was registered yet
        PaperGuiSettings.get().register(JavaPlugin.getProvidingPlugin(GuiBukkitListener.class));
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
