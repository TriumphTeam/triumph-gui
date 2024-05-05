package dev.triumphteam.gui.paper.builder.item;

import com.google.common.base.Preconditions;
import dev.triumphteam.gui.click.action.GuiClickAction;
import dev.triumphteam.gui.click.action.RunnableGuiClickAction;
import dev.triumphteam.gui.item.GuiItem;
import dev.triumphteam.gui.item.items.SimpleGuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractItemBuilder<B extends AbstractItemBuilder<B>> {

    private static final EnumSet<Material> LEATHER_ARMOR = EnumSet.of(
        Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS
    );

    private ItemStack itemStack;
    private ItemMeta meta;

    protected AbstractItemBuilder(final @NotNull ItemStack itemStack) {
        Preconditions.checkNotNull(itemStack, "Item can't be null!");

        this.itemStack = itemStack;
        meta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
    }

    /**
     * Sets the display name of the item using {@link Component}
     *
     * @param name The {@link Component} name
     * @return {@link B}
     * @since 3.0.0
     */
    @NotNull
    @Contract("_ -> this")
    public B name(final @NotNull Component name) {
        if (meta == null) return (B) this;

        meta.displayName(name);
        return (B) this;
    }

    /**
     * Sets the amount of items
     *
     * @param amount the amount of items
     * @return {@link B}
     * @since 3.0.0
     */
    @NotNull
    @Contract("_ -> this")
    public B amount(final int amount) {
        itemStack.setAmount(amount);
        return (B) this;
    }

    /**
     * Set the lore lines of an item
     *
     * @param lore Lore lines as varargs
     * @return {@link B}
     * @since 3.0.0
     */
    @NotNull
    @Contract("_ -> this")
    public B lore(final @Nullable Component @NotNull ... lore) {
        return lore(Arrays.asList(lore));
    }

    /**
     * Set the lore lines of an item
     *
     * @param lore A {@link List} with the lore lines
     * @return {@link B}
     * @since 3.0.0
     */
    @NotNull
    @Contract("_ -> this")
    public B lore(final @NotNull List<@Nullable Component> lore) {
        if (meta == null) return (B) this;
        meta.lore(lore);
        return (B) this;
    }

    @NotNull
    @Contract(" -> new")
    public ItemStack asItemStack() {
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    @NotNull
    @Contract("_ -> new")
    public GuiItem<Player, ItemStack> asGuiItem(final @NotNull RunnableGuiClickAction<Player> action) {
        return new SimpleGuiItem<>(asItemStack(), action);
    }

    @NotNull
    @Contract("_ -> new")
    public GuiItem<Player, ItemStack> asGuiItem(final @NotNull GuiClickAction<Player> action) {
        return new SimpleGuiItem<>(asItemStack(), action);
    }
}
