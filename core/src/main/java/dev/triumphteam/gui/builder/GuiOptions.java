package dev.triumphteam.gui.builder;

import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.components.ScrollType;
import dev.triumphteam.gui.guis.BaseGui;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface GuiOptions {

    @NotNull
    GuiType guiType();

    int rows();

    @NotNull
    ScrollType scrollType();

    @Contract("_ -> new")
    Inventory createInventory(@NotNull final BaseGui baseGui);

}
