package dev.triumphteam.gui.click.handler;

import dev.triumphteam.gui.click.ClickContext;
import dev.triumphteam.gui.click.action.GuiClickAction;
import dev.triumphteam.gui.click.completable.CompletableClick;
import org.jetbrains.annotations.NotNull;

public interface ClickHandler<P> {

    void handle(
        final @NotNull P player,
        final @NotNull ClickContext context,
        final @NotNull GuiClickAction<P> action,
        final @NotNull CompletableClick deferred
    );
}
