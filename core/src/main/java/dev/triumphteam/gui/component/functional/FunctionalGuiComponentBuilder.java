package dev.triumphteam.gui.component.functional;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface FunctionalGuiComponentBuilder<P, I> {

    void accept(final @NotNull FunctionalGuiComponent<@NotNull P, @NotNull I> component);
}
