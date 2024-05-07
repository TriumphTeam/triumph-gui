package dev.triumphteam.gui.click.processor;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import dev.triumphteam.gui.AbstractGuiView;
import dev.triumphteam.gui.click.ClickContext;
import dev.triumphteam.gui.click.action.EmptyGuiClickAction;
import dev.triumphteam.gui.click.controller.DefaultClickController;
import dev.triumphteam.gui.click.handler.ClickHandler;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * The job of the click processor is to handle everything related to a player clicking on a GUI.
 * The processor will only allow one click to go through per time, if a click is taking too long, it'll block
 * all other clicks from happening.
 * This is to prevent unwanted interactions.
 * <p>
 * The processor can also handle cases of players spam clicking.
 * The prevention duration can be configured.
 *
 * @param <P> The player type.
 * @param <I> The item type.
 */
public final class ClickProcessor<P, I> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClickProcessor.class);

    private final long spamPreventionDuration;
    private final Cache<UUID, LocalTime> spamPrevention;

    private boolean isProcessing = false;

    public ClickProcessor(final long spamPreventionDuration) {
        // Cache for spam prevention
        this.spamPreventionDuration = spamPreventionDuration;
        this.spamPrevention = CacheBuilder.newBuilder()
            .expireAfterWrite(spamPreventionDuration, TimeUnit.MILLISECONDS)
            .build();
    }

    /**
     * Process the current click.
     * Will mark the processor as busy once the click starts then may or may not free it once it's done processing.
     * A click can be blocked for a much longer time depending on the {@link ClickHandler} being used.
     *
     * @param slot The slot being clicked.
     * @param view The current view.
     */
    public void processClick(final int slot, final @NotNull AbstractGuiView<P, I> view) {

        final var viewerUuid = view.viewerUuid();

        // Check if the player can currently click
        if (!canClick(viewerUuid)) {
            return;
        }

        final var renderedItem = view.getItem(slot);
        if (renderedItem == null) return;

        final var action = renderedItem.action();
        // Early exit if action is empty
        if (action instanceof EmptyGuiClickAction<P>) {
            return;
        }

        // Start processing the click

        this.isProcessing = true;

        final var clickContext = new ClickContext();
        final var handler = renderedItem.clickHandler();

        // Prepare the controller with the whenComplete handler
        final var clickController = new DefaultClickController((ignored, throwable) -> {
            // If something went wrong with the click log, the error and stop processing
            if (throwable != null) {
                LOGGER.error(
                    "An exception occurred while processing click for '{}' on slot '{}'.",
                    view.viewerName(),
                    slot,
                    throwable
                );
            }

            this.isProcessing = false;
        });

        // The handler needs to catch so the click can finish
        // The exception is passed to the controller and still logged
        Exception handledException = null;
        try {
            handler.handle(view.viewer(), clickContext, action, clickController);
        } catch (final Exception exception) {
            handledException = exception;
        }

        // If not completing later makes sure to immediately complete it
        if (!clickController.completingLater()) {
            clickController.complete(handledException);
        }
    }

    /**
     * Checks if the player can currently click on the GUI.
     *
     * @param clickerUuid The UUID of the clicker.
     * @return True if the processor is not busy and not timed out from spamming.
     */
    private boolean canClick(final @NotNull UUID clickerUuid) {

        // If spam prevention is disabled, ignore it
        if (spamPreventionDuration != 0) {
            final var spamming = spamPrevention.getIfPresent(clickerUuid);
            if (spamming != null) {
                return false;
            }

            spamPrevention.put(clickerUuid, LocalTime.now());
        }

        // Check if the processor is not currently busy
        return !isProcessing;
    }
}
