package dev.triumphteam.gui.builder;

import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.guis.BaseGui;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public final class LegacyGuiTitle implements Title {

    private final String title;

    public LegacyGuiTitle(@NotNull final String title) {
        this.title = title;
    }

    @NotNull
    @Override
    public Inventory createInventory(final @NotNull BaseGui baseGui, final int rows) {
        return Bukkit.createInventory(baseGui, rows * 9, title);
    }

    @NotNull
    @Override
    public Inventory createInventory(final @NotNull BaseGui baseGui, @NotNull final GuiType guiType) {
        return Bukkit.createInventory(baseGui, guiType.getInventoryType(), title);
    }

}
