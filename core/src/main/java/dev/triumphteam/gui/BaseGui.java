package dev.triumphteam.gui;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface BaseGui<P, V extends BaseGuiView<P, V>> {

    @NotNull V open(final @NotNull P player, final @Nullable V parent);
}
