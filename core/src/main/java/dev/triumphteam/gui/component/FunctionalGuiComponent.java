package dev.triumphteam.gui.component;

import dev.triumphteam.gui.state.MutableState;
import dev.triumphteam.gui.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface FunctionalGuiComponent<P, I> {

    @NotNull
    State state(final @NotNull State value);

    <T> @NotNull MutableState<@NotNull T> state(final @NotNull T value);

    <T> @NotNull MutableState<@Nullable T> nullableState(final @Nullable T value);

    <T> @NotNull MutableState<T> state(final @NotNull MutableState<T> state);

    void render(final @NotNull GuiComponentRender<@NotNull P, @NotNull I> component);
}
