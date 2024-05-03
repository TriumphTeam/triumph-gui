package dev.triumphteam.gui.click.action;

import dev.triumphteam.gui.click.ClickContext;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface RunnableGuiClickAction<P> extends GuiClickAction<P> {

    void run(final @NotNull P player, final @NotNull ClickContext context);
}
