package dev.triumphteam.gui.state;

import dev.triumphteam.nova.State;

public interface ScrollableState<T> extends State, Iterable<PageEntry<T>> {

    void prev();

    void next();
}
