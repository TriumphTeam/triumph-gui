package dev.triumphteam.gui.component.renderer;

import dev.triumphteam.gui.BaseGuiView;
import dev.triumphteam.gui.component.GuiComponent;
import org.jetbrains.annotations.NotNull;

public interface GuiComponentRenderer<P, I> {

    void renderComponent(
        final @NotNull P player,
        final @NotNull GuiComponent<P, I> component,
        final @NotNull BaseGuiView<P, I> view
    );
}
