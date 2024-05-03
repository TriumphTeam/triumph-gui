package dev.triumphteam.gui.click.completable;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public final class DeferredClick implements CompletableClick {

    private final CompletableFuture<Boolean> deferred = CompletableFuture.completedFuture(null);
    private boolean completingLater = false;

    public DeferredClick(final @NotNull BiConsumer<Boolean, ? super Throwable> onComplete) {
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
