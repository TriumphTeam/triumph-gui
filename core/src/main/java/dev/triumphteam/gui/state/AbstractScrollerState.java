package dev.triumphteam.gui.state;

import dev.triumphteam.gui.layout.GuiLayout;
import dev.triumphteam.nova.AbstractState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractScrollerState<T> extends AbstractState implements ScrollerState<T> {

    private final int steps;
    private final int elementsPerView;
    private final List<T> elements;
    private final GuiLayout layout;
    private final int limit;

    private int current;

    public AbstractScrollerState(
        final int steps,
        final int startPosition,
        final @NotNull List<T> elements,
        final @NotNull GuiLayout layout
    ) {
        this.elementsPerView = layout.size();

        // If no steps we use all elements per view
        if (steps < 1) this.steps = this.elementsPerView;
        else this.steps = steps;

        this.current = startPosition;
        this.elements = elements;
        this.layout = layout;
        this.limit = (int) Math.ceil((double) elements.size() / steps);
    }

    @Override
    public void next() {
        if (current >= limit) return;
        current += steps;
        trigger();
    }

    @Override
    public void prev() {
        if (current <= 1) return;
        current -= steps;
        trigger();
    }

    @Override
    public @NotNull Iterator<PageEntry<T>> iterator() {
        final var from = current - 1;
        final var to = Math.min(from + elementsPerView, elements.size());
        final var subList = new ArrayList<PageEntry<T>>();
        final var layoutIterator = layout.iterator();

        for (int i = from; i < to; i++) {
            if (!layoutIterator.hasNext()) break;
            final var slot = layoutIterator.next();

            subList.add(new PageEntry<>(slot, elements.get(i)));
        }

        return subList.iterator();
    }

    protected int getSteps() {
        return steps;
    }

    protected int getElementsPerView() {
        return elementsPerView;
    }

    protected List<T> getElements() {
        return elements;
    }

    protected int getLimit() {
        return limit;
    }

    protected int getCurrent() {
        return current;
    }
}
