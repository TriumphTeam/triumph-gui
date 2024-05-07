package dev.triumphteam.gui.click.controller;

import org.jetbrains.annotations.Nullable;

/**
 * TODO
 */
public interface ClickController {

    boolean isDone();

    void complete(final @Nullable Throwable throwable);

    boolean completingLater();

    void completingLater(final boolean value);
}
