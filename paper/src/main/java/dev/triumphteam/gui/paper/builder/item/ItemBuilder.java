package dev.triumphteam.gui.paper.builder.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class ItemBuilder extends AbstractItemBuilder<ItemBuilder> {

    public ItemBuilder(final @NotNull ItemStack itemStack) {
        super(itemStack);
    }

    public static ItemBuilder from(final @NotNull ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    public static ItemBuilder from(final @NotNull Material material) {
        return new ItemBuilder(new ItemStack(material));
    }
}
