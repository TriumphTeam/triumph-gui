package dev.triumphteam.gui.nbt;

import org.jetbrains.annotations.NotNull;

public interface NbtContext {

    void set(@NotNull final String key, @NotNull final String value);

    String get(@NotNull final String key);

}
