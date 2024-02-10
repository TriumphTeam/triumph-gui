package dev.triumphteam.gui.guis;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

public class Task {

    public Task(Plugin plugin) {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            Bukkit.getOnlinePlayers().forEach(player -> {
                Inventory inventory = player.getOpenInventory().getTopInventory();
                if (!inventory.getType().equals(InventoryType.CRAFTING)) return;
                if (inventory.getHolder() instanceof BaseGui) {
                    BaseGui gui = (BaseGui) inventory.getHolder();
                    if (gui == null) return;
                    if (!gui.isAutoUpdate()) return;
                    if (gui.isUpdating()) return;
                    gui.update();
                }
            });
        }, 0, 20L);
    }
}
