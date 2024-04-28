package dev.triumphteam.gui.container;

import dev.triumphteam.gui.item.GuiItem;
import dev.triumphteam.gui.item.RenderedGuiItem;
import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class MapBackedContainer<I> implements GuiContainer<I> {

    private final Map<Slot, RenderedGuiItem<I>> backing = new HashMap<>(100);

    @Override
    public @NotNull GuiContainerType containerType() {
        return null;
    }

    @Override
    public void set(final int slot, final @NotNull GuiItem<@NotNull I> guiItem) {
        set(new Slot(slot), guiItem);
    }

    @Override
    public void set(final int row, final int column, final @NotNull GuiItem<@NotNull I> guiItem) {
        // TODO(matt): This
        set(new Slot(0), guiItem);
    }

    @Override
    public void set(final @NotNull Slot slot, final @NotNull GuiItem<@NotNull I> guiItem) {
        // Render item
        final var renderedItem = new RenderedGuiItem<>(guiItem.render(), guiItem.getClickAction());
        // Add rendered to backing
        backing.put(slot, renderedItem);
    }

    public @NotNull Map<Slot, RenderedGuiItem<I>> complete() {
        return Collections.unmodifiableMap(backing);
    }
}
