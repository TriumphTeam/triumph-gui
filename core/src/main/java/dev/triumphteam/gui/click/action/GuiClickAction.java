package dev.triumphteam.gui.click.action;

import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.click.handler.CompletableFutureClickHandler;
import dev.triumphteam.gui.click.handler.SimpleClickHandler;

/**
 * Represents an action for when a player clicks on the GUI.
 * This interface is empty to allow users to implement their own.
 * The implementation of the click action is entirely dependent on the
 * {@link ClickHandler} used.
 *
 * @param <P> The player type.
 * @see RunnableGuiClickAction
 * @see ClickHandler
 * @see SimpleClickHandler
 * @see CompletableFutureClickHandler
 */
public interface GuiClickAction<P> {}
