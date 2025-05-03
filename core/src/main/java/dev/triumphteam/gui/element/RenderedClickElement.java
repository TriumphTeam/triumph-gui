package dev.triumphteam.gui.element;

import dev.triumphteam.gui.click.action.GuiClickAction;
import dev.triumphteam.gui.click.handler.ClickHandler;
import org.jetbrains.annotations.NotNull;

public record RenderedClickElement<P, I>(
        @NotNull ClickHandler<P> clickHandler,
        @NotNull GuiClickAction<P> action
) implements RenderedGuiElement<P, I> {

    @Override
    public @NotNull ClickHandler<P> getClickHandler() {
        return clickHandler;
    }

    @Override
    public @NotNull GuiClickAction<P> getAction() {
        return action;
    }
}
