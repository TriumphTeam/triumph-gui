package me.mattstudios.mfgui.gui;

import me.mattstudios.mfgui.gui.components.GuiClickResolver;
import me.mattstudios.mfgui.gui.components.ItemNBT;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;
import java.util.UUID;

public final class GuiItem extends ItemStack implements Cloneable, ConfigurationSerializable {

    private GuiClickResolver action;

    private Material type = Material.AIR;
    private int amount = 0;
    private ItemMeta meta;

    private final UUID uuid = UUID.randomUUID();


    public GuiItem(final ItemStack itemStack, final GuiClickResolver action) {
        if (action == null) this.action = event -> {};
        else this.action = action;

        type = itemStack.getType();
        amount = itemStack.getAmount();
        meta = itemStack.getItemMeta();

        ItemStack nbted = ItemNBT.setNBTTag(itemStack, "test", "fuck you");
        System.out.println(ItemNBT.getNBTTag(nbted, "test"));
    }

    public GuiItem(final ItemStack itemStack) {
        this(itemStack, null);
    }

    public GuiClickResolver getAction() {
        return action;
    }

    @Override
    public Map<String, Object> serialize() {
        return null;
    }
}
