package dev.triumphteam.gui.item.items;

import dev.triumphteam.gui.item.GuiItem;
import dev.triumphteam.gui.click.action.GuiClickAction;
import org.jetbrains.annotations.NotNull;

public final class SimpleGuiItem<I> implements GuiItem<I> {

    private final I item;
    private final GuiClickAction clickAction;

    public SimpleGuiItem(final @NotNull I item, final @NotNull GuiClickAction clickAction) {
        this.item = item;
        this.clickAction = clickAction;
    }

    @Override
    public @NotNull I render() {
        return item;
    }

    @Override
    public @NotNull GuiClickAction getClickAction() {
        return clickAction;
    }
}
