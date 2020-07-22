package me.mattstudios.mfgui.gui.components;

import org.bukkit.event.inventory.InventoryType;

public enum GuiType {

    CHEST(InventoryType.CHEST),
    ANVIL(InventoryType.ANVIL),
    WORKBENCH(InventoryType.WORKBENCH);

    private final InventoryType inventoryType;

    GuiType(final InventoryType inventoryType) {
        this.inventoryType = inventoryType;
    }

    public InventoryType getInventoryType() {
        return inventoryType;
    }

}
