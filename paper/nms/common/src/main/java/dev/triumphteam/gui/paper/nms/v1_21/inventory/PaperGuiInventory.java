package dev.triumphteam.gui.paper.nms.v1_21.inventory;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface PaperGuiInventory {

    void open();

    void setItem(final int slot, final @NotNull ItemStack itemStack);

    void clearSlot(final int slot);

    @NotNull Inventory getBukkitInventory();

    default void restorePlayerInventory() {}
}
