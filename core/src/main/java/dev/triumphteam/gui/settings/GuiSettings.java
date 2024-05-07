package dev.triumphteam.gui.settings;

import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.click.handler.SimpleClickHandler;
import dev.triumphteam.gui.component.renderer.DefaultGuiComponentRenderer;
import dev.triumphteam.gui.component.renderer.GuiComponentRenderer;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unchecked")
public abstract class GuiSettings<P, I, S extends GuiSettings<P, I, S>> {

    private ClickHandler<P> clickHandler = new SimpleClickHandler<>();
    private GuiComponentRenderer<P, I> componentRenderer = new DefaultGuiComponentRenderer<>();
    private long spamPreventionDuration = 200L;

    public S clickHandler(final @NotNull ClickHandler<P> clickHandler) {
        this.clickHandler = clickHandler;
        return (S) this;
    }

    public S componentRenderer(final @NotNull GuiComponentRenderer<P, I> componentRenderer) {
        this.componentRenderer = componentRenderer;
        return (S) this;
    }

    public S spamPreventionDuration(final long spamPreventionDuration) {
        if (spamPreventionDuration < 0L) {
            throw new IllegalArgumentException("Spam prevention duration cannot be negative!");
        }

        this.spamPreventionDuration = spamPreventionDuration;
        return (S) this;
    }

    public @NotNull ClickHandler<P> getClickHandler() {
        return clickHandler;
    }

    public @NotNull GuiComponentRenderer<P, I> getComponentRenderer() {
        return componentRenderer;
    }

    public long getSpamPreventionDuration() {
        return spamPreventionDuration;
    }
}
