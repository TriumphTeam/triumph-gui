package dev.triumphteam.gui;

import org.jetbrains.annotations.NotNull;

/**
 * Representation of a simple GUI.
 * The GUI itself does nothing other than be a blue-print for multiple {@link GuiView} views,
 * for {@link P} players to interact with.
 *
 * @param <P> A player.
 */
public interface BaseGui<P> {

    /**
     * Opens a {@link GuiView} of this GUI for the {@link P} player.
     *
     * @param player The player to show the view to.
     */
    void open(final @NotNull P player);
}
