package dev.triumphteam.gui.component;

import dev.triumphteam.gui.container.GuiContainer;
import org.jetbrains.annotations.NotNull;

public interface ReactiveGuiComponent<P, I> extends StatefulGuiComponent<P, I> {

    void render(final @NotNull GuiContainer<@NotNull I> container, final @NotNull P player);
}
