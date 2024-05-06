package dev.triumphteam.gui.click.action;

import dev.triumphteam.gui.click.ClickContext;
import dev.triumphteam.gui.click.handler.CompletableFutureClickHandler;
import dev.triumphteam.gui.click.handler.SimpleClickHandler;
import org.jetbrains.annotations.NotNull;

/**
 * The main/default click action type.
 * A simple {@link Runnable} like functional interface.
 * This is by default handled by {@link CompletableFutureClickHandler} and {@link SimpleClickHandler}.
 *
 * @param <P> The player type.
 * @see SimpleClickHandler
 * @see CompletableFutureClickHandler
 */
@FunctionalInterface
public interface RunnableGuiClickAction<P> extends GuiClickAction<P> {

    /**
     * Run the click action.
     *
     * @param player  The instance of the player clicking on the GUI.
     * @param context The click context.
     */
    void run(final @NotNull P player, final @NotNull ClickContext context);
}
