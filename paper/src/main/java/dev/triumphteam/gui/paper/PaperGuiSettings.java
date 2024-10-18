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
package dev.triumphteam.gui.paper;

import dev.triumphteam.gui.exception.TriumphGuiException;
import dev.triumphteam.gui.settings.GuiSettings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class PaperGuiSettings extends GuiSettings<Player, ItemStack, PaperGuiSettings> {

    private static final PaperGuiSettings INSTANCE = new PaperGuiSettings();

    private boolean listenerRegistered = false;
    private Plugin plugin = null;

    private PaperGuiSettings() {}

    public static PaperGuiSettings get() {
        return INSTANCE;
    }

    public PaperGuiSettings register(final @NotNull Plugin plugin) {
        // Only register listener once
        if (this.listenerRegistered) return this;

        this.plugin = plugin;
        Bukkit.getServer().getPluginManager().registerEvents(new PaperGuiListener(), plugin);
        this.listenerRegistered = true;
        return this;
    }

    public @NotNull Plugin getPlugin() {
        if (plugin == null) {
            throw new TriumphGuiException("An error occurred while attempting to get the plugin instance.");
        }

        return plugin;
    }
}
