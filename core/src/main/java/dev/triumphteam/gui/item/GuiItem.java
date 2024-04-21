package dev.triumphteam.gui.item;

import org.jetbrains.annotations.NotNull;

public interface GuiItem<I> {

    @NotNull
    I render();

    @NotNull
    ItemClickAction getClickAction();
}
