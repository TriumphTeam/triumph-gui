package dev.triumphteam.gui.container;

import dev.triumphteam.gui.element.GuiElement;
import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class MapBackedContainer<I> implements GuiContainer<I> {

    private final Map<Slot, GuiElement<I>> backing = new HashMap<>(100);

    @Override
    public void set(final int slot, final @NotNull GuiElement<@NotNull I> guiElement) {
        set(new Slot(slot), guiElement);
    }

    @Override
    public void set(final int row, final int column, final @NotNull GuiElement<@NotNull I> guiElement) {
        // TODO(matt): This
        set(new Slot(0), guiElement);
    }

    @Override
    public void set(final @NotNull Slot slot, final @NotNull GuiElement<@NotNull I> guiElement) {
        backing.put(slot, guiElement);
    }

    public @NotNull Map<Slot, GuiElement<I>> getBacking() {
        return backing;
    }
}
