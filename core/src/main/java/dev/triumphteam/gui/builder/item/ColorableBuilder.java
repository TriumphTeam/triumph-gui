package dev.triumphteam.gui.builder.item;

import org.bukkit.Color;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface ColorableBuilder<B extends BaseItemBuilder<B>> {

    /**
     * Color an {@link org.bukkit.inventory.ItemStack}
     *
     * @param color color
     * @return {@link B}
     * @see org.bukkit.inventory.meta.LeatherArmorMeta#setColor(Color)
     * @see org.bukkit.inventory.meta.MapMeta#setColor(Color)
     * @since 3.0.3
     */
    @NotNull
    B color(@NotNull final Color color);

}
