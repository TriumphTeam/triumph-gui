package dev.triumphteam.gui.container;

import dev.triumphteam.gui.item.GuiItem;
import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class MapBackedContainer<I> implements GuiContainer<I> {

    private final Map<Slot, GuiItem<I>> backing = new HashMap<>(100);

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
        backing.put(slot, guiItem);
    }

    public @NotNull Map<Slot, GuiItem<I>> getBacking() {
        return backing;
    }
}
