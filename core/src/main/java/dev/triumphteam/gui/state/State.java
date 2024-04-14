package dev.triumphteam.gui.state;

import dev.triumphteam.gui.BaseGuiView;
import org.jetbrains.annotations.NotNull;

public interface State<T> {

    T getValue();

    void setValue(final T value);

    void force();

    void addListener(
        final @NotNull BaseGuiView<?, ?> view,
        final @NotNull Runnable runnable
    );
}
