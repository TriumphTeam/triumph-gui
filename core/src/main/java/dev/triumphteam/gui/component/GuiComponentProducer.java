package dev.triumphteam.gui.component;

import org.jetbrains.annotations.NotNull;

public interface GuiComponentProducer<P, I> {

    @NotNull
    GuiComponent<P, I> asGuiComponent();
}
