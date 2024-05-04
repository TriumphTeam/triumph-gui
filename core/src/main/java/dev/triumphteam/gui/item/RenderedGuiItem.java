package dev.triumphteam.gui.item;

import dev.triumphteam.gui.click.action.GuiClickAction;
import dev.triumphteam.gui.click.handler.ClickHandler;
import org.jetbrains.annotations.NotNull;

public record RenderedGuiItem<P, I>(
    @NotNull I item,
    @NotNull ClickHandler<P> clickHandler,
    @NotNull GuiClickAction<P> action
) {}
