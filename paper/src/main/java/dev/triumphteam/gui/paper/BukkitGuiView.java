package dev.triumphteam.gui.paper;

import dev.triumphteam.gui.AbstractGuiView;
import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.click.processor.ClickProcessor;
import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.renderer.GuiComponentRenderer;
import dev.triumphteam.gui.item.RenderedGuiItem;
import dev.triumphteam.gui.paper.container.type.PaperContainerType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class BukkitGuiView extends AbstractGuiView<Player, ItemStack> implements InventoryHolder {

    private final Inventory inventory;

    public BukkitGuiView(
        final @NotNull Player player,
        final @NotNull Component title,
        final @NotNull PaperContainerType containerType,
        final @NotNull List<GuiComponent<Player, ItemStack>> components,
        final @NotNull GuiComponentRenderer<Player, ItemStack> componentRenderer,
        final @NotNull ClickHandler<Player> clickHandler,
        final long spamPreventionDuration
    ) {
        super(player, components, containerType, componentRenderer, clickHandler, new ClickProcessor<>(spamPreventionDuration));
        this.inventory = containerType.createInventory(this, title);
    }

    @Override
    public void open() {
        viewer().openInventory(inventory);
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
    protected void clearSlot(final int slot) {
        inventory.clear(slot);
    }

    @Override
    public @NotNull String viewerName() {
        return viewer().getName();
    }

    @Override
    public @NotNull UUID viewerUuid() {
        return viewer().getUniqueId();
    }

    @Override
    protected void populateInventory(final @NotNull Map<Integer, RenderedGuiItem<Player, ItemStack>> renderedItems) {
        renderedItems.forEach((slot, item) -> inventory.setItem(slot, item.item()));
    }
}
