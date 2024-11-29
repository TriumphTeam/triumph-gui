/**
 * MIT License
 * <p>
 * Copyright (c) 2024 TriumphTeam
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.gui.paper;

import dev.triumphteam.gui.actions.GuiCloseAction;
import dev.triumphteam.gui.click.ClickContext;
import dev.triumphteam.gui.click.GuiClick;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PaperGuiListener implements Listener {

    public static void register() {
        // Auto-register listener if none was registered yet
        PaperGuiSettings.get().register(JavaPlugin.getProvidingPlugin(PaperGuiListener.class));
    }

    @EventHandler
    public void onGuiClick(final @NotNull InventoryClickEvent event) {
        final var view = convertHolder(event.getInventory().getHolder());
        if (view == null) return;

        event.setCancelled(true);
        view.processClick(new ClickContext(mapClick(event.getClick()), event.getSlot()));
    }

    @EventHandler
    public void onGuiDrag(final @NotNull InventoryDragEvent event) {
        final var view = convertHolder(event.getInventory().getHolder());
        if (view == null) return;

        event.setCancelled(true);
    }

    @EventHandler
    public void onGuiOpen(final @NotNull InventoryOpenEvent event) {
        final var view = convertHolder(event.getInventory().getHolder());
        if (view == null) return;

        if (!view.isUpdating()) return;
        event.titleOverride(view.getTitle());
    }

    @EventHandler
    public void onGuiClose(final @NotNull InventoryCloseEvent event) {
        final var view = convertHolder(event.getInventory().getHolder());
        if (view == null) return;

        if (view.isUpdating()) return;
        view.getCloseActions().forEach(GuiCloseAction::onClose);
    }

    private @Nullable PaperGuiView convertHolder(final @Nullable InventoryHolder holder) {
        if (holder == null) return null;
        if (holder instanceof PaperGuiView paperGuiView) return paperGuiView;
        return null;
    }

    private @NotNull GuiClick mapClick(final @NotNull ClickType clickType) {
        return switch (clickType) {
            case ClickType.LEFT -> GuiClick.LEFT;
            case ClickType.SHIFT_LEFT -> GuiClick.SHIFT_LEFT;
            case ClickType.RIGHT -> GuiClick.RIGHT;
            case ClickType.SHIFT_RIGHT -> GuiClick.SHIFT_RIGHT;
            case ClickType.WINDOW_BORDER_LEFT -> GuiClick.WINDOW_BORDER_LEFT;
            case ClickType.WINDOW_BORDER_RIGHT -> GuiClick.WINDOW_BORDER_RIGHT;
            case ClickType.MIDDLE -> GuiClick.MIDDLE;
            case ClickType.NUMBER_KEY -> GuiClick.NUMBER_KEY;
            case ClickType.DOUBLE_CLICK -> GuiClick.DOUBLE_CLICK;
            case ClickType.DROP -> GuiClick.DROP;
            case ClickType.CONTROL_DROP -> GuiClick.CONTROL_DROP;
            case ClickType.CREATIVE -> GuiClick.CREATIVE;
            case ClickType.SWAP_OFFHAND -> GuiClick.SWAP_OFFHAND;
            case ClickType.UNKNOWN -> GuiClick.UNKNOWN;
        };
    }
}
