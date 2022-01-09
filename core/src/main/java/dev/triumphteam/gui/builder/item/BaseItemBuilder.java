/**
 * MIT License
 *
 * Copyright (c) 2021 TriumphTeam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.gui.builder.item;

import dev.triumphteam.gui.components.GuiAction;
import dev.triumphteam.gui.components.util.ItemNbt;
import dev.triumphteam.gui.components.util.Legacy;
import dev.triumphteam.gui.components.util.VersionHelper;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Contains all the common methods for the future ItemBuilders
 *
 * @param <B> The ItemBuilder type so the methods can cast to the subtype
 */
@SuppressWarnings("unchecked")
public abstract class BaseItemBuilder<B extends BaseItemBuilder<B>> implements ColorableBuilder<B> {

    private static final EnumSet<Material> LEATHER_ARMOR = EnumSet.of(
            Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS
    );

    private ItemStack itemStack;
    private ItemMeta meta;

    protected BaseItemBuilder(@NotNull final ItemStack itemStack) {
        Validate.notNull(itemStack, "Item can't be null!");

        this.itemStack = itemStack;
        meta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
    }

    /**
     * Sets the display name of the item using {@link Component}
     *
     * @param name The {@link Component} name
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @NotNull
    @Contract("_ -> this")
    public B name(@NotNull final Component name) {
        meta.setDisplayName(Legacy.SERIALIZER.serialize(name));
        return (B) this;
    }

    /**
     * Sets the amount of items
     *
     * @param amount the amount of items
     * @return {@link ItemBuilder}
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
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @NotNull
    @Contract("_ -> this")
    public B lore(@NotNull final Component... lore) {
        return lore(Arrays.asList(lore));
    }

    /**
     * Set the lore lines of an item
     *
     * @param lore A {@link List} with the lore lines
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @NotNull
    @Contract("_ -> this")
    public B lore(@NotNull final List<Component> lore) {
        meta.setLore(lore.stream().map(Legacy.SERIALIZER::serialize).collect(Collectors.toList()));
        return (B) this;
    }

    /**
     * Consumer for freely adding to the lore
     *
     * @param lore A {@link Consumer} with the {@link List} of lore {@link Component}
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @NotNull
    @Contract("_ -> this")
    public B lore(@NotNull final Consumer<List<Component>> lore) {
        final List<String> strings = meta.hasLore() ? meta.getLore() : new ArrayList<>();
        final List<Component> components = strings.stream().map(Legacy.SERIALIZER::deserialize).collect(Collectors.toList());

        lore.accept(components);
        return lore(components);
    }

    /**
     * Enchants the {@link ItemStack}
     *
     * @param enchantment            The {@link Enchantment} to add
     * @param level                  The level of the {@link Enchantment}
     * @param ignoreLevelRestriction If should or not ignore it
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @NotNull
    @Contract("_, _, _ -> this")
    public B enchant(@NotNull final Enchantment enchantment, final int level, final boolean ignoreLevelRestriction) {
        meta.addEnchant(enchantment, level, ignoreLevelRestriction);
        return (B) this;
    }

    /**
     * Enchants the {@link ItemStack}
     *
     * @param enchantment The {@link Enchantment} to add
     * @param level       The level of the {@link Enchantment}
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @NotNull
    @Contract("_, _ -> this")
    public B enchant(@NotNull final Enchantment enchantment, final int level) {
        return enchant(enchantment, level, true);
    }

    /**
     * Enchants the {@link ItemStack}
     *
     * @param enchantment The {@link Enchantment} to add
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @NotNull
    @Contract("_ -> this")
    public B enchant(@NotNull final Enchantment enchantment) {
        return enchant(enchantment, 1, true);
    }

    /**
     * Disenchants a certain {@link Enchantment} from the {@link ItemStack}
     *
     * @param enchantment The {@link Enchantment} to remove
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @NotNull
    @Contract("_ -> this")
    public B disenchant(@NotNull final Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return (B) this;
    }

    /**
     * Add an {@link ItemFlag} to the item
     *
     * @param flags The {@link ItemFlag} to add
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @NotNull
    @Contract("_ -> this")
    public B flags(@NotNull final ItemFlag... flags) {
        meta.addItemFlags(flags);
        return (B) this;
    }

    /**
     * Makes the {@link ItemStack} unbreakable
     *
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @NotNull
    @Contract(" -> this")
    public B unbreakable() {
        return unbreakable(true);
    }

    /**
     * Sets the item as unbreakable
     *
     * @param unbreakable If should or not be unbreakable
     * @return {@link ItemBuilder}
     */
    @NotNull
    @Contract("_ -> this")
    public B unbreakable(boolean unbreakable) {
        if (VersionHelper.IS_UNBREAKABLE_LEGACY) {
            return setNbt("Unbreakable", unbreakable);
        }

        meta.setUnbreakable(unbreakable);
        return (B) this;
    }

    /**
     * Makes the {@link ItemStack} glow
     *
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @NotNull
    @Contract(" -> this")
    public B glow() {
        return glow(true);
    }

    /**
     * Adds or removes the {@link ItemStack} glow
     *
     * @param glow Should the item glow
     * @return {@link ItemBuilder}
     */
    @NotNull
    @Contract("_ -> this")
    public B glow(boolean glow) {
        if (glow) {
            meta.addEnchant(Enchantment.LURE, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            return (B) this;
        }

        for (final Enchantment enchantment : meta.getEnchants().keySet()) {
            meta.removeEnchant(enchantment);
        }

        return (B) this;
    }

    /**
     * Consumer for applying {@link PersistentDataContainer} to the item
     * This method will only work on versions above 1.14
     *
     * @param consumer The {@link Consumer} with the PDC
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @NotNull
    @Contract("_ -> this")
    public B pdc(@NotNull final Consumer<PersistentDataContainer> consumer) {
        consumer.accept(meta.getPersistentDataContainer());
        return (B) this;
    }

    /**
     * Sets the custom model data of the item
     * Added in 1.13
     *
     * @param modelData The custom model data from the resource pack
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @NotNull
    @Contract("_ -> this")
    public B model(final int modelData) {
        if (VersionHelper.IS_CUSTOM_MODEL_DATA) {
            meta.setCustomModelData(modelData);
        }

        return (B) this;
    }

    /**
     * {@inheritDoc}
     * @param color color
     * @return {@link ItemBuilder}
     * @since 3.0.3
     */
    @NotNull
    @Contract("_ -> this")
    public B color(@NotNull final Color color) {
        if (LEATHER_ARMOR.contains(itemStack.getType())) {
            final LeatherArmorMeta lam = (LeatherArmorMeta) getMeta();

            lam.setColor(color);
            setMeta(lam);
        }

        return (B) this;
    }

    /**
     * Sets NBT tag to the {@link ItemStack}
     *
     * @param key   The NBT key
     * @param value The NBT value
     * @return {@link ItemBuilder}
     */
    @NotNull
    @Contract("_, _ -> this")
    public B setNbt(@NotNull final String key, @Nullable final String value) {
        itemStack.setItemMeta(meta);
        itemStack = ItemNbt.setString(itemStack, key, value);
        meta = itemStack.getItemMeta();
        return (B) this;
    }

    /**
     * Sets NBT tag to the {@link ItemStack}
     *
     * @param key   The NBT key
     * @param value The NBT value
     * @return {@link ItemBuilder}
     */
    @NotNull
    @Contract("_, _ -> this")
    public B setNbt(@NotNull final String key, final boolean value) {
        itemStack.setItemMeta(meta);
        itemStack = ItemNbt.setBoolean(itemStack, key, value);
        meta = itemStack.getItemMeta();
        return (B) this;
    }

    /**
     * Removes NBT tag from the {@link ItemStack}
     *
     * @param key The NBT key
     * @return {@link ItemBuilder}
     */
    @NotNull
    @Contract("_ -> this")
    public B removeNbt(@NotNull final String key) {
        itemStack.setItemMeta(meta);
        itemStack = ItemNbt.removeTag(itemStack, key);
        meta = itemStack.getItemMeta();
        return (B) this;
    }

    /**
     * Builds the item into {@link ItemStack}
     *
     * @return The fully built {@link ItemStack}
     */
    @NotNull
    public ItemStack build() {
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * Creates a {@link GuiItem} instead of an {@link ItemStack}
     *
     * @return A {@link GuiItem} with no {@link GuiAction}
     */
    @NotNull
    @Contract(" -> new")
    public GuiItem asGuiItem() {
        return new GuiItem(build());
    }

    /**
     * Creates a {@link GuiItem} instead of an {@link ItemStack}
     *
     * @param action The {@link GuiAction} to apply to the item
     * @return A {@link GuiItem} with {@link GuiAction}
     */
    @NotNull
    @Contract("_ -> new")
    public GuiItem asGuiItem(@NotNull final GuiAction<InventoryClickEvent> action) {
        return new GuiItem(build(), action);
    }

    /**
     * Package private getter for extended builders
     *
     * @return The ItemStack
     */
    @NotNull
    protected ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * Package private setter for the extended builders
     *
     * @param itemStack The ItemStack
     */
    protected void setItemStack(@NotNull final ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Package private getter for extended builders
     *
     * @return The ItemMeta
     */
    @NotNull
    protected ItemMeta getMeta() {
        return meta;
    }

    /**
     * Package private setter for the extended builders
     *
     * @param meta The ItemMeta
     */
    protected void setMeta(@NotNull final ItemMeta meta) {
        this.meta = meta;
    }

}
