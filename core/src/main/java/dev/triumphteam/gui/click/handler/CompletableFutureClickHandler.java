package dev.triumphteam.gui.click.handler;

import dev.triumphteam.gui.click.ClickContext;
import dev.triumphteam.gui.click.action.GuiClickAction;
import dev.triumphteam.gui.click.action.RunnableGuiClickAction;
import dev.triumphteam.gui.click.completable.ClickController;
import dev.triumphteam.gui.exception.TriumphGuiException;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * A {@link ClickHandler} implementation that runs the click action async using {@link CompletableFuture}.
 *
 * @param <P> The player type.
 */
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
        final @NotNull ClickController controller
    ) {
        // Only accept runnable actions
        if (!(action instanceof RunnableGuiClickAction<P>)) {
            throw new TriumphGuiException("The click action type '" + action.getClass().getName() + "' is supported by the 'CompletableFutureClickHandler'.");
        }

        // Tell the controller that it'll be complete later as to block all new clicks
        controller.completingLater(true);

        // Run the action async and complete click when finished
        CompletableFuture.runAsync(() -> ((RunnableGuiClickAction<P>) action).run(player, context))
            .orTimeout(timeout, unit)
            .whenComplete((unused, throwable) -> controller.complete(throwable));
    }
}
