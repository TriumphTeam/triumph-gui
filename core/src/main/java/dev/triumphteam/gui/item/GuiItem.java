package dev.triumphteam.gui.item;

import dev.triumphteam.gui.click.action.GuiClickAction;
import org.jetbrains.annotations.NotNull;

public interface GuiItem<P, I> {

    @NotNull
    I render();

    @NotNull
    GuiClickAction<P> getClickAction();
}
