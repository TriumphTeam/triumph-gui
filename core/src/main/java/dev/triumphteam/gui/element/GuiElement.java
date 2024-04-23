package dev.triumphteam.gui.element;

import org.jetbrains.annotations.NotNull;

public interface GuiElement<I> {

    @NotNull
    I render();

    @NotNull
    ItemClickAction getClickAction();
}
