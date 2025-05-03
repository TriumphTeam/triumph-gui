package dev.triumphteam.gui.element;

import dev.triumphteam.gui.click.action.GuiClickAction;
import org.jetbrains.annotations.NotNull;

public interface GuiElement<P, I> {

    @NotNull
    GuiClickAction<P> getClickAction();
}
