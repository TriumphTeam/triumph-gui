package dev.triumphteam.gui.state.pagination;

import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public record PageEntry<T>(@NotNull Slot slot, @NotNull T element) implements Map.Entry<Slot, T> {
    @Override
    public Slot getKey() {
        return slot;
    }

    @Override
    public T getValue() {
        return element;
    }

    @Override
    public T setValue(T value) {
        throw new UnsupportedOperationException("PageEntry is immutable.");
    }
}
