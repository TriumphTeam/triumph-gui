package dev.triumphteam.gui.component.renderer;

import dev.triumphteam.gui.AbstractGuiView;
import dev.triumphteam.gui.component.GuiComponent;
import org.jetbrains.annotations.NotNull;

public interface GuiComponentRenderer<P, I> {

    void renderComponent(
        final @NotNull P player,
        final @NotNull GuiComponent<P, I> component,
        final @NotNull AbstractGuiView<P, I> view
    );
}
