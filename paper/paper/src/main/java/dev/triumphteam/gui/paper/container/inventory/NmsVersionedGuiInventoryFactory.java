package dev.triumphteam.gui.paper.container.inventory;

import dev.triumphteam.gui.paper.PaperGuiInventory;
import dev.triumphteam.gui.paper.nms.v1_21.NmsInventoryFactory;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public final class NmsVersionedGuiInventoryFactory {

    private NmsVersionedGuiInventoryFactory() {
        throw new AssertionError("Util class, cannot be instantiated");
    }

    public static @NotNull PaperGuiInventory chest(
            final @NotNull InventoryHolder holder,
            final @NotNull org.bukkit.entity.Player player,
            final @NotNull Component title,
            final int rows
    ) {
        // TODO: Add more versions, currently we use only 1.21.5
        return NmsInventoryFactory.chest(holder, player, title, rows);
    }

    public static @NotNull PaperGuiInventory anvil(
            final @NotNull InventoryHolder holder,
            final @NotNull org.bukkit.entity.Player player,
            final @NotNull Component title,
            final boolean usePlayerInventory
    ) {
        // TODO: Add more versions, currently we use only 1.21.5
        return NmsInventoryFactory.anvil(holder, player, title, usePlayerInventory);
    }
}
