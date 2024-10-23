package dev.triumphteam.gui.state;

import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

public record PageEntry<T>(@NotNull Slot slot, @NotNull T element) {
}
