package dev.triumphteam.gui.component.functional;

import dev.triumphteam.gui.component.GuiComponent;
import org.jetbrains.annotations.NotNull;

public interface GuiComponentProducer<P, I> {

    @NotNull
    GuiComponent<P, I> asGuiComponent();
}
