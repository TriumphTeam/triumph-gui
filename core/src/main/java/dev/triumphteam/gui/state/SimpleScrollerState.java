package dev.triumphteam.gui.state;

import dev.triumphteam.gui.layout.GuiLayout;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SimpleScrollerState<T> extends AbstractScrollerState<T> {

    public SimpleScrollerState(
            final int steps,
            final @NotNull List<T> elements,
            final @NotNull GuiLayout layout
    ) {
        super(steps, 1, elements, layout);
    }
}
