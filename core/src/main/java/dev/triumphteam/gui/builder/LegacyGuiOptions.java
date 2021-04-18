package dev.triumphteam.gui.builder;

import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.components.ScrollType;
import dev.triumphteam.gui.guis.BaseGui;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public final class LegacyGuiOptions implements GuiOptions {

    private final String title;
    private final int rows;
    private final GuiType guiType;
    private final ScrollType scrollType;

    public LegacyGuiOptions(@NotNull final String title, final int rows, @NotNull final GuiType guiType, @NotNull final ScrollType scrollType) {
        int finalRows = rows;
        if (!(rows >= 1 && rows <= 6)) finalRows = 1;

        this.rows = finalRows;
        this.title = title;
        this.guiType = guiType;
        this.scrollType = scrollType;
    }

    public LegacyGuiOptions(@NotNull final String title, final int rows, @NotNull final GuiType guiType) {
        this(title, rows, guiType, ScrollType.VERTICAL);
    }

    public LegacyGuiOptions(@NotNull final String title, final int rows) {
        this(title, rows, GuiType.CHEST);
    }

    public LegacyGuiOptions(@NotNull final String title, @NotNull final GuiType guiType) {
        this(title, 1, guiType);
    }

    public LegacyGuiOptions(@NotNull final String title, final int rows, @NotNull final ScrollType scrollType) {
        this(title, 1, GuiType.CHEST, scrollType);
    }

    @NotNull
    @Override
    public GuiType guiType() {
        return guiType;
    }

    @Override
    public int rows() {
        return rows;
    }

    @NotNull
    @Override
    public ScrollType scrollType() {
        return scrollType;
    }

    @NotNull
    @Override
    public Inventory createInventory(final @NotNull BaseGui baseGui) {
        return Bukkit.createInventory(baseGui, rows * 9, title);
    }

}
