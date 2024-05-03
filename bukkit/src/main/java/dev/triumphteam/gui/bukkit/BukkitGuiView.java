package dev.triumphteam.gui.bukkit;

import dev.triumphteam.gui.AbstractGuiView;
import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.renderer.DefaultGuiComponentRenderer;
import dev.triumphteam.gui.item.RenderedGuiItem;
import dev.triumphteam.gui.slot.Slot;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public final class BukkitGuiView extends AbstractGuiView<Player, ItemStack> implements InventoryHolder {

    private final Inventory inventory;

    public BukkitGuiView(
        final @NotNull Player player,
        final @NotNull List<GuiComponent<Player, ItemStack>> components
    ) {
        // TODO(matt): Renderer from constructor
        super(player, components, new DefaultGuiComponentRenderer<>());

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
    protected void clearSlot(final @NotNull Slot slot) {
        inventory.clear(slot.slot());
    }

    @Override
    protected void populateInventory(final @NotNull Map<Slot, RenderedGuiItem<ItemStack>> renderedItems) {
        renderedItems.forEach((slot, item) -> inventory.setItem(slot.slot(), item.item()));
    }
}
