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

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class GuiBukkitListener implements Listener {

    public static void register() {
        // Auto-register listener if none was registered yet
        PaperGuiSettings.get().register(JavaPlugin.getProvidingPlugin(GuiBukkitListener.class));
    }

    @EventHandler
    public void onGuiClick(final @NotNull InventoryClickEvent event) {
        final var holder = event.getInventory().getHolder();
        if (!(holder instanceof final BukkitGuiView view)) {
            return;
        }

        event.setCancelled(true);
        view.getClickProcessor().processClick(event.getSlot(), view);
    }

    @EventHandler
    public void onInventoryOpen(final @NotNull InventoryOpenEvent event) {
        final var holder = event.getInventory().getHolder();
        if (!(holder instanceof final BukkitGuiView view)) {
            return;
        }

        if (!view.isFirstOpen()) {
            event.titleOverride(view.getTitle());
        }
    }
}
