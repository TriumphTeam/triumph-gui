package dev.triumphteam.gui.components;

import net.kyori.adventure.text.Component;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public interface ContainerType {

    @NotNull Component title();

    void title(final @NotNull Component title);

    @NotNull Inventory createInventory(final @NotNull InventoryHolder inventoryHolder);

    int inventorySize();

    int inventorySize

    class Chest implements ContainerType {

        private final InventoryProvider.Chest inventoryProvider;
        private Component title;
        private final int rows;

        public Chest(
                final @NotNull Component title,
                final @NotNull InventoryProvider.Chest inventoryProvider,
                final int rows
        ) {
            this.inventoryProvider = inventoryProvider;
            this.title = title;
            this.rows = rows;
        }

        @Override
        public @NotNull Component title() {
            return title;
        }

        @Override
        public void title(final @NotNull Component title) {
            this.title = title;
        }

        @Override
        public int inventorySize() {
            return rows * 9;
        }

        @Override
        public @NotNull Inventory createInventory(final @NotNull InventoryHolder inventoryHolder) {
            return inventoryProvider.getInventory(title, inventoryHolder, rows);
        }
    }

    class NonChest implements ContainerType {

        private final InventoryProvider.NonChest inventoryProvider;
        private Component title;
        private final InventoryType inventoryType;

        public NonChest(
                final @NotNull Component title,
                final @NotNull InventoryProvider.NonChest inventoryProvider,
                final @NotNull InventoryType inventoryType
        ) {
            this.inventoryProvider = inventoryProvider;
            this.title = title;
            this.inventoryType = inventoryType;
        }

        @Override
        public @NotNull Component title() {
            return title;
        }

        @Override
        public void title(@NotNull Component title) {
            this.title = title;
        }

        @Override
        public int inventorySize() {
            return inventoryType.getDefaultSize();
        }

        @Override
        public @NotNull Inventory createInventory(@NotNull InventoryHolder inventoryHolder) {
            return inventoryProvider.getInventory(title, inventoryHolder, inventoryType);
        }
    }
}
