/**
 * MIT License
 *
 * Copyright (c) 2024 TriumphTeam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
