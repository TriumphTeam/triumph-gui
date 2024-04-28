package dev.triumphteam.gui.item;

import org.jetbrains.annotations.NotNull;

public record RenderedGuiItem<I>(@NotNull I item, @NotNull ItemClickAction action) {
}
