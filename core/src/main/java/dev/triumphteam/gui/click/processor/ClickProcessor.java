package dev.triumphteam.gui.click.processor;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dev.triumphteam.gui.AbstractGuiView;
import dev.triumphteam.gui.click.ClickContext;
import dev.triumphteam.gui.click.completable.DeferredClick;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class ClickProcessor<P, I> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClickProcessor.class);

    private final Cache<UUID, LocalTime> spamPrevention = Caffeine.newBuilder()
        .expireAfterWrite(200L, TimeUnit.MILLISECONDS)
        .build();

    private final AtomicBoolean isProcessing = new AtomicBoolean(false);

    public void processClick(final int slot, final @NotNull AbstractGuiView<P, I> view) {

        final var viewerUuid = view.viewerUuid();

        if (!canClick(viewerUuid)) {
            return;
        }

        if (!isProcessing.compareAndSet(false, true)) {
            return;
        }

        final var clickContext = new ClickContext();

        final var renderedItem = view.getItem(slot);
        if (renderedItem == null) return;

        final var handler = renderedItem.clickHandler();

        final var deferredClick = new DeferredClick((ignored, throwable) -> {
            if (throwable != null) {
                LOGGER.error(
                    "An exception occurred while processing click for '{}' on slot '{}'.",
                    view.viewerName(),
                    slot,
                    throwable
                );
            }

            this.isProcessing.compareAndSet(true, false);
        });

        handler.handle(view.viewer(), clickContext, renderedItem.action(), deferredClick);

        if (!deferredClick.completingLater()) {
            deferredClick.complete(null);
        }
    }

    private boolean canClick(final @NotNull UUID clickerUuid) {
        if (isProcessing.get()) {
            return false;
        }

        final var spamming = spamPrevention.getIfPresent(clickerUuid);
        if (spamming == null) {
            spamPrevention.put(clickerUuid, LocalTime.now());
            return true;
        }

        return false;
    }
}
