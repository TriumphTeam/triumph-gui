package dev.triumphteam.gui.state;

import dev.triumphteam.gui.BaseGuiView;
import org.jetbrains.annotations.NotNull;

public final class SimpleState<T> implements MutableState<T> {

    private final StateMap stateMap = new StateMap();
    private T value;

    public SimpleState(final T value) {
        this.value = value;
    }

    @Override
    public T getValue() {
        return value;
    }

    @Override
    public void setValue(final T value) {
        this.value = value;
        trigger();
    }

    @Override
    public void trigger() {
        stateMap.run();
    }

    @Override
    public void addListener(final @NotNull BaseGuiView<?, ?> view, final @NotNull Runnable runnable) {
        stateMap.put(view, runnable);
    }
}
