package dev.triumphteam.gui.component;

import dev.triumphteam.gui.BaseGuiView;
import dev.triumphteam.gui.state.State;
import org.jetbrains.annotations.NotNull;

public interface GuiComponentRenderer<P, V extends BaseGuiView<P, V>> {

    State<Integer> state(final int value);

    <T> State<T> state(final @NotNull State<T> state);

    void render(final @NotNull GuiComponent<P, V> component);
}
