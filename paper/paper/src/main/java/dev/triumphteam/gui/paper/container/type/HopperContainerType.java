package dev.triumphteam.gui.paper.container.type;

import dev.triumphteam.gui.container.type.GuiContainerType;
import dev.triumphteam.gui.exception.TriumphGuiException;
import dev.triumphteam.gui.slot.Slot;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class HopperContainerType implements PaperContainerType {

    private static final int LOWER_LIMIT = 0;
    private static final int UPPER_LIMIT = 5;

    public static @NotNull GuiContainerType of() {
        return new HopperContainerType();
    }

    @Override
    public int mapSlot(final @NotNull Slot slot) {
        final var realSlot = (slot.column() * slot.row()) - 1;

        if (realSlot < LOWER_LIMIT || realSlot > UPPER_LIMIT) {
            throw new TriumphGuiException(
                "Invalid slot (" + slot.row() + ", " + slot.column() + "). Valid range is (1, 1) to (1, 5)."
            );
        }

        return realSlot;
    }

    @Override
    public @NotNull Slot mapSlot(final int slot) {
        return Slot.of(1, slot + 1);
    }

    @Override
    public @NotNull Inventory createInventory(
        final @NotNull InventoryHolder holder,
        final @NotNull Component title
    ) {
        return Bukkit.createInventory(holder, InventoryType.HOPPER, title);
    }
}
