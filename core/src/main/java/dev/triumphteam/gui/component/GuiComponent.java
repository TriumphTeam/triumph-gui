package dev.triumphteam.gui.component;

import dev.triumphteam.gui.container.GuiContainer;
import dev.triumphteam.gui.state.State;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface GuiComponent<P, I> {

    @NotNull
    List<@NotNull State> states();

    void render(final @NotNull GuiContainer<@NotNull I> container, final @NotNull P player);
}
