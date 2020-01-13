package me.mattstudios.mfgui.gui;

import me.mattstudios.mfgui.gui.components.GuiClickResolver;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public final class GuiItem {

    private GuiClickResolver action;

    private final ItemStack itemStack;

    private final UUID uuid = UUID.randomUUID();


    public GuiItem(final ItemStack itemStack, final GuiClickResolver action) {
        if (action == null) this.action = event -> {};
        else this.action = action;

        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public GuiClickResolver getAction() {
        return action;
    }
}
