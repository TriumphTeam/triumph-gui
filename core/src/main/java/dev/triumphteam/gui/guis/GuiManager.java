package dev.triumphteam.gui.guis;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Currently this is the solution to not implementing {@link org.bukkit.inventory.InventoryHolder}
 * I am not that happy with this solution, so if anyone has a better solution feel free to PR it
 */
final class GuiManager {

    @NotNull
    private final Map<Inventory, BaseGui> openedGuis = new HashMap<>();

    /**
     * Main constructor of the manager
     * Used for registering the GUI listener
     *
     * @param plugin The instance of the plugin calling the GUI
     */
    GuiManager(@NotNull final Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(new GuiListener(this), plugin);

        // Testing
        Bukkit.getScheduler().runTaskTimer(plugin, () -> System.out.println(openedGuis), 20L, 20L);
    }

    /**
     * Gets the {@link BaseGui} that is associated with that {@link Inventory}
     *
     * @param inventory The event {@link Inventory}
     * @return The associated {@link BaseGui} or null if the inventory is not present
     */
    @Nullable
    public BaseGui getGui(@NotNull final Inventory inventory) {
        return openedGuis.get(inventory);
    }

    /**
     * Adds the {@link BaseGui} to the openedGuis map
     *
     * @param inventory The associated {@link Inventory}
     * @param gui       The {@link BaseGui} that is being opened
     */
    public void addGui(@NotNull final Inventory inventory, @NotNull final BaseGui gui) {
        openedGuis.putIfAbsent(inventory, gui);
    }

    /**
     * Removes the {@link BaseGui} from the map if there is no longer any player viewing the {@link Inventory}
     *
     * @param inventory The event {@link Inventory}
     */
    public void removeGui(@NotNull final Inventory inventory) {
        if (inventory.getViewers().size() > 1) return;
        openedGuis.remove(inventory);
    }

}
