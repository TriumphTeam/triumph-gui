package dev.triumphteam.gui.components;

import dev.triumphteam.gui.guis.BaseGui;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public interface GuiOptions {

    @NotNull
    Inventory createInventory(@NotNull final BaseGui baseGui);

}
