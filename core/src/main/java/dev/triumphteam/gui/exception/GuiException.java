package dev.triumphteam.gui.exception;

import org.jetbrains.annotations.NotNull;

public final class GuiException extends RuntimeException {

    public GuiException(final @NotNull String message) {
        super(message);
    }
}
