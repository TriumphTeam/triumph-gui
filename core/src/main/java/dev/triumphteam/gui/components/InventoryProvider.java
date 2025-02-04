package dev.triumphteam.gui.components;

import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public final class InventoryProvider {

    @FunctionalInterface
    public interface Chest {

        @NotNull Inventory getInventory(
                final @NotNull Component title,
                final @NotNull InventoryHolder owner,
                final int rows
        );
    }

    @FunctionalInterface
    public interface Typed {

        @NotNull Inventory getInventory(
                final @NotNull Component title,
                final @NotNull InventoryHolder owner,
                final @NotNull InventoryType inventoryType
        );
    }
}
