package dev.triumphteam.gui.click.controller;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * TODO
 */
public final class DefaultClickController implements ClickController {

    private final CompletableFuture<Boolean> deferred = new CompletableFuture<>();
    private boolean completingLater = false;

    public DefaultClickController(final @NotNull BiConsumer<Boolean, ? super Throwable> onComplete) {
        deferred.whenComplete(onComplete);
    }

    @Override
    public boolean isDone() {
        return deferred.isDone() || deferred.isCancelled();
    }

    @Override
    public void complete(final @Nullable Throwable throwable) {
        if (isDone()) return;

        if (throwable != null) {
            deferred.completeExceptionally(throwable);
            return;
        }

        deferred.complete(true);
    }

    @Override
    public boolean completingLater() {
        return completingLater;
    }

    @Override
    public void completingLater(final boolean value) {
        this.completingLater = value;
    }
}
