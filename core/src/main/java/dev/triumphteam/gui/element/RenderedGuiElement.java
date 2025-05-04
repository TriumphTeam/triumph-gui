package dev.triumphteam.gui.element;

import dev.triumphteam.gui.click.action.GuiClickAction;
import dev.triumphteam.gui.click.handler.ClickHandler;
import org.jetbrains.annotations.NotNull;

public interface RenderedGuiElement<P, I> {

    @NotNull ClickHandler<P> getClickHandler();

    @NotNull GuiClickAction<P> getAction();
}
