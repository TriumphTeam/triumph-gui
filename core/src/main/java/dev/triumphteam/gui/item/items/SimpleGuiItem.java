package dev.triumphteam.gui.item.items;

import dev.triumphteam.gui.click.action.GuiClickAction;
import dev.triumphteam.gui.item.GuiItem;
import org.jetbrains.annotations.NotNull;

public final class SimpleGuiItem<P, I> implements GuiItem<P, I> {

    private final I item;
    private final GuiClickAction<P> clickAction;

    public SimpleGuiItem(final @NotNull I item, final @NotNull GuiClickAction<P> clickAction) {
        this.item = item;
        this.clickAction = clickAction;
    }

    @Override
    public @NotNull I render() {
        return item;
    }

    @Override
    public @NotNull GuiClickAction<P> getClickAction() {
        return clickAction;
    }
}
