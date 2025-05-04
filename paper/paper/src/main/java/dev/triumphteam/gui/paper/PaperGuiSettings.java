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

import dev.triumphteam.gui.settings.GuiSettings;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class PaperGuiSettings extends GuiSettings<Player, ItemStack, PaperGuiSettings> {

    private static final PaperGuiSettings INSTANCE = new PaperGuiSettings();

    private Plugin plugin = null;

    private PaperGuiSettings() {}

    public static PaperGuiSettings get() {
        return INSTANCE;
    }

    public static PaperGuiSettings init(final @NotNull Plugin plugin) {
        return INSTANCE.setPlugin(plugin);
    }

    public @NotNull Plugin getPlugin() {
        if (plugin == null) {
            init(JavaPlugin.getProvidingPlugin(PaperGuiListener.class));
        }

        return plugin;
    }

    private PaperGuiSettings setPlugin(final @NotNull Plugin plugin) {
        if (this.plugin != null) return this;
        this.plugin = plugin;
        return this;
    }
}
