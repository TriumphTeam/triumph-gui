package dev.triumphteam.gui.component.functional;

import dev.triumphteam.gui.container.GuiContainer;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface GuiComponentRender<P, I> {

    void render(
        final @NotNull GuiContainer<@NotNull P, @NotNull I> container,
        final @NotNull P player
    );
}
