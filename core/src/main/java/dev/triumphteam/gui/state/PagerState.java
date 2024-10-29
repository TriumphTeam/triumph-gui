package dev.triumphteam.gui.state;

import dev.triumphteam.gui.layout.GuiLayout;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface PagerState<T> extends ScrollerState<T> {

    static <T> @NotNull PagerState<T> of(
            final int startPage,
            final @NotNull List<T> elements,
            final @NotNull GuiLayout layout
    ) {
        return new SimplePagerState<>(startPage, elements, layout);
    }

    static <T> @NotNull PagerState<T> of(
            final @NotNull List<T> elements,
            final @NotNull GuiLayout layout
    ) {
        return of(1, elements, layout);
    }

    int getCurrentPage();

    int getPageCount();
}
