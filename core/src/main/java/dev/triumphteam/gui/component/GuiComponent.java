package dev.triumphteam.gui.component;

import dev.triumphteam.gui.BaseGuiView;
import dev.triumphteam.gui.container.Container;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface GuiComponent<P, V extends BaseGuiView<P, V>> {

    void render(
        final @NotNull Container container,
        final @NotNull P player,
        final @NotNull V view
    );
}
