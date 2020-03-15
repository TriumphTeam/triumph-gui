package me.mattstudios.mfgui.gui.guis;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public final class PaginationGui extends BaseGui {

    public PaginationGui(@NotNull final Plugin plugin, final int rows, @NotNull final String title) {
        super(plugin, rows, title);
    }

    public PaginationGui(@NotNull final Plugin plugin, @NotNull final String title) {
        super(plugin, title);
    }

}
