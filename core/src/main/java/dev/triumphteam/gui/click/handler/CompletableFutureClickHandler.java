package dev.triumphteam.gui.click.handler;

import dev.triumphteam.gui.click.ClickContext;
import dev.triumphteam.gui.click.action.GuiClickAction;
import dev.triumphteam.gui.click.action.RunnableGuiClickAction;
import dev.triumphteam.gui.click.completable.CompletableClick;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public final class CompletableFutureClickHandler<P> implements ClickHandler<P> {

    private final long timeout;
    private final TimeUnit unit;

    public CompletableFutureClickHandler() {
        this(6, TimeUnit.SECONDS);
    }

    public CompletableFutureClickHandler(final long timeout, final @NotNull TimeUnit unit) {
        this.timeout = timeout;
        this.unit = unit;
    }

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

        deferred.completingLater(true);

        CompletableFuture.runAsync(() -> ((RunnableGuiClickAction<P>) action).run(player, context))
            .orTimeout(timeout, unit)
            .whenComplete((unused, throwable) -> deferred.complete(throwable));
    }
}
