package dev.triumphteam.gui.title.renderer;

import dev.triumphteam.gui.title.GuiTitle;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface GuiTitleRenderer {

    void renderTitle(
        final @NotNull GuiTitle title,
        final @NotNull Consumer<Component> thenRun
    );
}
