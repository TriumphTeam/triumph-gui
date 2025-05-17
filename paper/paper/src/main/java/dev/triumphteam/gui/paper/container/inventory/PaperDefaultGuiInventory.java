package dev.triumphteam.gui.paper.container.inventory;

import dev.triumphteam.gui.paper.container.type.PaperContainerType;
import dev.triumphteam.gui.paper.nms.PaperGuiInventory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class PaperDefaultGuiInventory implements PaperGuiInventory {

    private final Player player;
    private final PaperContainerType containerType;
    private final Inventory inventory;
    private final Inventory playerInventory;

    public PaperDefaultGuiInventory(
            final @NotNull Player player,
            final @NotNull PaperContainerType containerType,
            final @NotNull Inventory inventory
    ) {
        this.player = player;
        this.containerType = containerType;
        this.inventory = inventory;
        this.playerInventory = player.getInventory();
    }

    @Override
    public void open() {
        player.openInventory(inventory);
    }

    @Override
    public @NotNull Inventory getBukkitInventory() {
        return inventory;
    }

    @Override
    public void setTopInventoryItem(final int slot, final @NotNull ItemStack itemStack) {
        inventory.setItem(containerType.toTopInventory(slot), itemStack);
    }

    @Override
    public void setPlayerInventoryItem(final int slot, final @NotNull ItemStack itemStack) {
        playerInventory.setItem(containerType.toPlayerInventory(slot), itemStack);
    }

    @Override
    public void clearTopInventorySlot(final int slot) {
        inventory.clear(containerType.toTopInventory(slot));
    }

    @Override
    public void clearPlayerInventorySlot(final int slot) {
        playerInventory.clear(containerType.toPlayerInventory(slot));
    }
}
