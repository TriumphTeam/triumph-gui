package dev.triumphteam.gui.state.pagination;

import dev.triumphteam.gui.layout.GuiLayout;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class SimplePagerState<T> extends AbstractScrollerState<T> implements PagerState<T> {

    public SimplePagerState(
        final int startPage,
        final @NotNull List<T> elements,
        final @NotNull GuiLayout layout
    ) {
        super(0, startPage, elements, layout);
    }

    @Override
    public int getCurrentPage() {
        return getCurrent();
    }

    @Override
    public int getPageCount() {
        return getLimit();
    }
}
