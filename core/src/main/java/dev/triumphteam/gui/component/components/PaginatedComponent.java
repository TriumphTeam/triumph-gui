package dev.triumphteam.gui.component.components;

import dev.triumphteam.gui.component.ReactiveGuiComponent;
import dev.triumphteam.gui.container.GuiContainer;
import dev.triumphteam.gui.state.State;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class PaginatedComponent<P, I> implements ReactiveGuiComponent<P, I> {

    private final List<I> items;

    public PaginatedComponent(final @NotNull List<I> items) {
        this.items = items;
    }

    @Override
    public @NotNull List<@NotNull State> states() {
        return List.of();
    }

    @Override
    public void render(final @NotNull GuiContainer<I> container, final @NotNull  P player) {
        container.set(22, );
        container.set(23, );


    }
}
