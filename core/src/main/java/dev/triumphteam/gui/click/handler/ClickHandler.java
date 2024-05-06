package dev.triumphteam.gui.click.handler;

import dev.triumphteam.gui.click.ClickContext;
import dev.triumphteam.gui.click.action.GuiClickAction;
import dev.triumphteam.gui.click.completable.ClickController;
import org.jetbrains.annotations.NotNull;

/**
 * The click handler is responsible for handling a {@link GuiClickAction}.
 * This can have many different behaviors, like sync clicks, async clicks, Kotlin suspending clicks, etc.
 * It is left to the user to implement other behaviors.
 *
 * @param <P> The player instance.
 * @see CompletableFutureClickHandler
 * @see SimpleClickHandler
 * @see GuiClickAction
 */
public interface ClickHandler<P> {

    /**
     * Handle the click action given the {@link ClickController}.
     *
     * @param player     The player clicking in the GUI.
     * @param context    The click context, containing information about the click.
     * @param action     The action to run.
     * @param controller The controller which can be used to control how the click is processed.
     */
    void handle(
        final @NotNull P player,
        final @NotNull ClickContext context,
        final @NotNull GuiClickAction<P> action,
        final @NotNull ClickController controller
    );
}
