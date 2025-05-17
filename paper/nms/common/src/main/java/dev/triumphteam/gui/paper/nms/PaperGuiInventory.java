package dev.triumphteam.gui.paper.nms;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public interface PaperGuiInventory {

    void open();

    void setTopInventoryItem(final int slot, final @NotNull ItemStack itemStack);

    void setPlayerInventoryItem(final int slot, final @NotNull ItemStack itemStack);

    void clearTopInventorySlot(final int slot);
    
    void clearPlayerInventorySlot(final int slot);

    @NotNull Inventory getBukkitInventory();

    default void restorePlayerInventory() {}
}
