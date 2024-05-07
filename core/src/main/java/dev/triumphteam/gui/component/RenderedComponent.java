package dev.triumphteam.gui.component;

import dev.triumphteam.gui.item.RenderedGuiItem;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public record RenderedComponent<P, I>(
    @NotNull GuiComponent<P, I> component,
    @NotNull Map<Integer, RenderedGuiItem<P, I>> renderedItems
) {
}
