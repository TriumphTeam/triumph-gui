package dev.triumphteam.gui.paper.container.type;

import dev.triumphteam.gui.container.type.GuiContainerType;
import net.kyori.adventure.text.Component;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public interface PaperContainerType extends GuiContainerType {

    @NotNull
    Inventory createInventory(
        final @NotNull InventoryHolder holder,
        final @NotNull Component title
    );
}
