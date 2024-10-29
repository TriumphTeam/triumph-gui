package dev.triumphteam.gui.state;

import dev.triumphteam.gui.layout.GuiLayout;
import dev.triumphteam.nova.State;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ScrollerState<T> extends State, Iterable<PageEntry<T>> {

    static <T> @NotNull ScrollerState<T> of(
            final int steps,
            final @NotNull List<T> elements,
            final @NotNull GuiLayout layout
    ) {
        return new SimpleScrollerState<>(steps, elements, layout);
    }

    void prev();

    void next();
}
