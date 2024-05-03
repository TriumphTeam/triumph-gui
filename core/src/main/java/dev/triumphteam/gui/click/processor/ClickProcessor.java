package dev.triumphteam.gui.click.processor;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dev.triumphteam.gui.AbstractGuiView;
import dev.triumphteam.gui.click.handler.ClickHandler;
import kotlin.Unit;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

public final class ClickProcessor<P, I> {

    private final Cache<UUID, Unit> spamPrevention = Caffeine.newBuilder()
        .expireAfterWrite(200L, TimeUnit.MILLISECONDS)
        .build();

    private final AtomicBoolean isProcessing = new AtomicBoolean(false);

    public void processClick(
        final int slot,
        final @NotNull UUID clickerUuid,
        final @NotNull AbstractGuiView<P, I> view
    ) {

        if (canClick(clickerUuid)) {
            return;
        }

        if (!isProcessing.compareAndSet(false, true)) {
            return;
        }

        // context

        final var action = view.getAction(slot);
        final var handler = clickHandlerSupplier.get();
        handler.handle(action);
    }

    private boolean canClick(final @NotNull UUID clickerUuid) {
        if (isProcessing.get()) {
            return false;
        }

        final var spamming = spamPrevention.getIfPresent(clickerUuid);
        if (spamming != null) {
            spamPrevention.put(clickerUuid, spamming);
            return true;
        }

        return false;
    }
}
