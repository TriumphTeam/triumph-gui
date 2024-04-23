package dev.triumphteam.gui.component;

import dev.triumphteam.gui.container.GuiContainer;
import org.jetbrains.annotations.NotNull;

public interface GuiComponent<P, I> extends ReactiveGuiComponent {

    void render(final @NotNull GuiContainer<@NotNull I> container, final @NotNull P player);
}
