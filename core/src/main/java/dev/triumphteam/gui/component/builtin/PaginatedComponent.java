package dev.triumphteam.gui.component.builtin;

import dev.triumphteam.gui.component.ReactiveGuiComponent;
import dev.triumphteam.gui.container.GuiContainer;
import dev.triumphteam.gui.state.State;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class PaginatedComponent<P, I> implements ReactiveGuiComponent<P, I> {

    @Override
    public @NotNull List<@NotNull State> states() {
        return List.of();
    }

    @Override
    public void render(final @NotNull GuiContainer<@NotNull I> container, @NotNull final P player) {

    }
}
