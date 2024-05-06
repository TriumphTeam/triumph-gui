package dev.triumphteam.gui.click.handler;

import dev.triumphteam.gui.click.ClickContext;
import dev.triumphteam.gui.click.action.GuiClickAction;
import dev.triumphteam.gui.click.action.RunnableGuiClickAction;
import dev.triumphteam.gui.click.completable.ClickController;
import dev.triumphteam.gui.exception.TriumphGuiException;
import org.jetbrains.annotations.NotNull;

/**
 * The simplest click handler, all it does is run the action given.
 *
 * @param <P> The player type.
 * @see ClickHandler
 * @see GuiClickAction
 * @see CompletableFutureClickHandler for async handler.
 */
public final class SimpleClickHandler<P> implements ClickHandler<P> {

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

        // Run the action
        ((RunnableGuiClickAction<P>) action).run(player, context);
    }
}
