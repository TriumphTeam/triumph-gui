package dev.triumphteam.gui.bukkit;

import dev.triumphteam.gui.component.FinalComponent;
import dev.triumphteam.gui.container.MapBackedContainer;
import dev.triumphteam.gui.element.RenderedItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class GuiView extends dev.triumphteam.gui.GuiView<Player, ItemStack> implements InventoryHolder {

    private final Inventory inventory;

    public GuiView(
        final @NotNull Player player,
        final @NotNull List<FinalComponent<Player, ItemStack>> componentRenderers
    ) {
        super(player, componentRenderers);
        this.inventory = Bukkit.createInventory(this, 54, "Gui");
    }

    @Override
    public void open() {
        getViewer().openInventory(inventory);
        setup();
    }

    @Override
    public void close() {

    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    protected void populateInventory(final @NotNull MapBackedContainer<ItemStack> container) {
        inventory.clear();
        container.getBacking().forEach((slot, baseGuiItem) -> {
            final var rendered = baseGuiItem.render();
            final var inventorySlot = slot.asRealSlot();
            temporaryCache.put(inventorySlot, new RenderedItem<>(rendered, baseGuiItem.getClickAction()));
            inventory.setItem(inventorySlot, rendered);
        });
    }
}
