package dev.triumphteam.gui.click.handler;

import dev.triumphteam.gui.click.ClickContext;
import dev.triumphteam.gui.click.action.GuiClickAction;
import dev.triumphteam.gui.click.action.RunnableGuiClickAction;
import dev.triumphteam.gui.click.completable.CompletableClick;
import org.jetbrains.annotations.NotNull;

public final class SimpleClickHandler<P> implements ClickHandler<P> {

    @Override
    public void handle(
        final @NotNull P player,
        final @NotNull ClickContext context,
        final @NotNull GuiClickAction<P> action,
        final @NotNull CompletableClick deferred
    ) {

        if (!(action instanceof RunnableGuiClickAction<P>)) {
            return;
        }

        ((RunnableGuiClickAction<P>) action).run(player, context);
    }
}
