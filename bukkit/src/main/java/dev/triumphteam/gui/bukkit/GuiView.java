package dev.triumphteam.gui.bukkit;

import dev.triumphteam.gui.BaseGuiView;
import dev.triumphteam.gui.component.FinalComponent;
import dev.triumphteam.gui.container.Container;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class GuiView extends BaseGuiView<Player, GuiView> implements InventoryHolder {

    private final Inventory inventory;

    public GuiView(
        final @NotNull Player player,
        final @Nullable GuiView parent,
        final @NotNull List<FinalComponent<Player, GuiView>> componentRenderers
    ) {
        super(player, parent, componentRenderers);
        this.inventory = Bukkit.createInventory(this, 6, "Gui");
    }

    @Override
    public void open() {
        getViewer().openInventory(inventory);
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
    protected void populateInventory(final @NotNull Container container) {
        inventory.addItem()
    }
}
