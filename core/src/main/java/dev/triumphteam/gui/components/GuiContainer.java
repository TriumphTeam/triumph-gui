package dev.triumphteam.gui.components;

import net.kyori.adventure.text.Component;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public interface GuiContainer {

    @NotNull Component title();

    void title(final @NotNull Component title);

    @NotNull Inventory createInventory(final @NotNull InventoryHolder inventoryHolder);

    @NotNull GuiType guiType();

    int inventorySize();

    int rows();

    class Chest implements GuiContainer {

        private final InventoryProvider.Chest inventoryProvider;

        private int rows;
        private Component title;

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
        public @NotNull GuiType guiType() {
            return GuiType.CHEST;
        }

        @Override
        public int rows() {
            return rows;
        }

        public void rows(final int rows) {
            this.rows = rows;
        }

        @Override
        public @NotNull Inventory createInventory(final @NotNull InventoryHolder inventoryHolder) {
            return inventoryProvider.getInventory(title, inventoryHolder, inventorySize());
        }
    }

    class Typed implements GuiContainer {

        private final InventoryProvider.Typed inventoryProvider;
        private final GuiType guiType;
        private Component title;

        public Typed(
            final @NotNull Component title,
            final @NotNull InventoryProvider.Typed inventoryProvider,
            final @NotNull GuiType guiType
        ) {
            this.inventoryProvider = inventoryProvider;
            this.title = title;
            this.guiType = guiType;
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
            return guiType.getLimit();
        }

        @Override
        public @NotNull GuiType guiType() {
            return guiType;
        }

        @Override
        public int rows() {
            return 1;
        }

        @Override
        public @NotNull Inventory createInventory(@NotNull InventoryHolder inventoryHolder) {
            return inventoryProvider.getInventory(title, inventoryHolder, guiType.getInventoryType());
        }
    }
}
