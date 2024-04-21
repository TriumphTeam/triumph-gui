package dev.triumphteam.gui.component;

import dev.triumphteam.gui.exception.GuiException;
import dev.triumphteam.gui.state.MutableState;
import dev.triumphteam.gui.state.SimpleState;
import dev.triumphteam.gui.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class SimpleFunctionalGuiComponent<P, I> implements FunctionalGuiComponent<P, I> {

    private final List<State<?>> states = new ArrayList<>();
    private GuiComponentRender<P, I> component = null;

    @Override
    public @NotNull State state(final @NotNull State value) {
        return null;
    }

    @Override
    public @NotNull <T> MutableState<@NotNull T> state(@NotNull final T value) {
        return null;
    }

    @Override
    public @NotNull <T> MutableState<@Nullable T> nullableState(@Nullable final T value) {
        return null;
    }

    @Override
    public @NotNull <T> MutableState<T> state(final @NotNull MutableState<T> state) {
        return null;
    }

    @Override
    public void render(final @NotNull GuiComponentRender<P, I> component) {
        this.component = component;
    }

    /*public @NotNull FinalComponent<P, I> createComponent() {
        if (component == null) {
            throw new GuiException("TODO");
        }

        return new FinalComponent<>(states, component);
    }*/
}
