package me.mattstudios.mfgui.gui.guis;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class PaginatedGui extends BaseGui {

    private final List<GuiItem> pageItems = new ArrayList<>();

    public PaginatedGui(@NotNull final Plugin plugin, final int rows, @NotNull final String title) {
        super(plugin, rows, title);

        if (rows < 2) setRows(2);
    }

    public PaginatedGui(@NotNull final Plugin plugin, @NotNull final String title) {
        this(plugin, 2, title);
    }

    @Override
    public BaseGui addItem(@NotNull final GuiItem... items) {
        pageItems.addAll(Arrays.asList(items));
        return this;
    }
}
