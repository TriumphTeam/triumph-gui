package dev.triumphteam.gui.item;

import dev.triumphteam.gui.click.action.GuiClickAction;
import org.jetbrains.annotations.NotNull;

public record RenderedGuiItem<P, I>(
    @NotNull I item,
    @NotNull GuiClickAction<P> action
) {}
