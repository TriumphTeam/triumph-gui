package dev.triumphteam.gui.container;

import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.item.GuiItem;
import dev.triumphteam.gui.item.RenderedGuiItem;
import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class MapBackedContainer<P, I> implements GuiContainer<P, I> {

    private final Map<Slot, RenderedGuiItem<P, I>> backing = new HashMap<>(100);
    private final ClickHandler<P> clickHandler;

    public MapBackedContainer(final @NotNull ClickHandler<P> clickHandler) {
        this.clickHandler = clickHandler;
    }

    @Override
    public @NotNull GuiContainerType containerType() {
        return null;
    }

    @Override
    public void set(final int slot, final @NotNull GuiItem<@NotNull P, @NotNull I> guiItem) {
        set(new Slot(slot), guiItem);
    }

    @Override
    public void set(final int row, final int column, final @NotNull GuiItem<@NotNull P, @NotNull I> guiItem) {
        set(Slot.fromRowCol(row, column), guiItem);
    }

    @Override
    public void set(final @NotNull Slot slot, final @NotNull GuiItem<@NotNull P, @NotNull I> guiItem) {
        // Render item
        final var renderedItem = new RenderedGuiItem<>(guiItem.render(), clickHandler, guiItem.getClickAction());
        // Add rendered to backing
        backing.put(slot, renderedItem);
    }

    public @NotNull Map<Slot, RenderedGuiItem<P, I>> complete() {
        return Collections.unmodifiableMap(backing);
    }
}
