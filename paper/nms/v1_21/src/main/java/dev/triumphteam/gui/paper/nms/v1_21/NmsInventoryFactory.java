package dev.triumphteam.gui.paper.nms.v1_21;

import dev.triumphteam.gui.paper.nms.v1_21.inventory.PaperGuiInventory;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public final class NmsInventoryFactory {

    public static @NotNull PaperGuiInventory chest(
            final @NotNull InventoryHolder holder,
            final @NotNull org.bukkit.entity.Player player,
            final @NotNull Component title,
            final int rows
    ) {
        return new NmsCombinedChestInventory(holder, player, title, rows);
    }

    public static @NotNull PaperGuiInventory anvil(
            final @NotNull InventoryHolder holder,
            final @NotNull org.bukkit.entity.Player player,
            final @NotNull Component title,
            final boolean usePlayerInventory
    ) {
        return new NmsAnvilInventory(holder, player, title, usePlayerInventory);
    }
}
