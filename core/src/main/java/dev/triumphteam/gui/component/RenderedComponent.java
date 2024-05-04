package dev.triumphteam.gui.component;

import dev.triumphteam.gui.item.RenderedGuiItem;
import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public record RenderedComponent<P, I>(
    @NotNull GuiComponent<P, I> component,
    @NotNull Map<Slot, RenderedGuiItem<P, I>> renderedItems
) {
}
