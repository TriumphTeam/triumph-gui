package dev.triumphteam.gui.title.renderer;

import dev.triumphteam.gui.exception.TriumphGuiException;
import dev.triumphteam.gui.title.GuiTitle;
import dev.triumphteam.gui.title.ReactiveGuiTitle;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public final class DefaultGuiTitleRenderer implements GuiTitleRenderer {

    @Override
    public void renderTitle(
        final @NotNull GuiTitle title,
        final @NotNull Consumer<Component> thenRun
    ) {

        if ((title instanceof ReactiveGuiTitle reactiveGuiTitle)) {
            final var component = reactiveGuiTitle.render();
            thenRun.accept(component);
            return;
        }

        throw new TriumphGuiException("Could not render title as it is not supported by the current renderer.");
    }
}
