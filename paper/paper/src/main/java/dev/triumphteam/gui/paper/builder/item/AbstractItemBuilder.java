/**
 * MIT License
 *
 * Copyright (c) 2024 TriumphTeam
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
package dev.triumphteam.gui.paper.builder.item;

import com.google.common.base.Preconditions;
import dev.triumphteam.gui.click.action.EmptyGuiClickAction;
import dev.triumphteam.gui.click.action.GuiClickAction;
import dev.triumphteam.gui.click.action.RunnableGuiClickAction;
import dev.triumphteam.gui.item.GuiItem;
import dev.triumphteam.gui.item.items.SimpleGuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@SuppressWarnings("unchecked")
public abstract class AbstractItemBuilder<B extends AbstractItemBuilder<B>> {

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
     */
    @NotNull
    @Contract("_ -> this")
    public B lore(final @NotNull List<@Nullable Component> lore) {
        if (meta == null) return (B) this;
        meta.lore(lore);
        return (B) this;
    }

    /**
     * Set the custom data model of an item
     *
     * @param modelData the data of the model to be set
     * @return {@link B}
     */
    @NotNull
    @Contract("_ -> this")
    public B model(final int modelData) {
        if (meta == null) return (B) this;
        meta.setCustomModelData(modelData);
        return (B) this;
    }

    /**
     * Makes the {@link ItemStack} glow
     *
     * @return {@link B}
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
     * @return {@link B}
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
     *
     * @param consumer The {@link Consumer} with the PDC
     * @return {@link B}
     */
    @NotNull
    @Contract("_ -> this")
    public B pdc(@NotNull final Consumer<PersistentDataContainer> consumer) {
        consumer.accept(meta.getPersistentDataContainer());
        return (B) this;
    }

    /**
     * Enchants the {@link ItemStack} with the specified {@link Enchantment} and level
     *
     * @param enchantment            The {@link Enchantment} to add
     * @param level                  The level of the {@link Enchantment}
     * @param ignoreLevelRestriction If should or not ignore it
     * @return {@link B}
     */
    @NotNull
    @Contract("_, _, _ -> this")
    public B enchant(@NotNull final Enchantment enchantment, final int level, final boolean ignoreLevelRestriction) {
        meta.addEnchant(enchantment, level, ignoreLevelRestriction);
        return (B) this;
    }

    /**
     * Enchants the {@link ItemStack} with the specified {@link Enchantment} and level
     *
     * @param enchantment The {@link Enchantment} to add
     * @param level       The level of the {@link Enchantment}
     * @return {@link B}
     */
    @NotNull
    @Contract("_, _ -> this")
    public B enchant(@NotNull final Enchantment enchantment, final int level) {
        return enchant(enchantment, level, true);
    }

    /**
     * Enchants the {@link ItemStack} with the specified {@link Enchantment}
     *
     * @param enchantment The {@link Enchantment} to add
     * @return {@link B}
     */
    @NotNull
    @Contract("_ -> this")
    public B enchant(@NotNull final Enchantment enchantment) {
        return enchant(enchantment, 1, true);
    }

    /**
     * Enchants the {@link ItemStack} with the specified map where the value
     * is the level of the key's enchantment
     *
     * @param enchantments           Enchantments to add
     * @param ignoreLevelRestriction If level restriction should be ignored
     * @return {@link B}
     * @since 3.1.2
     */
    @NotNull
    @Contract("_, _ -> this")
    public B enchant(@NotNull final Map<Enchantment, Integer> enchantments, final boolean ignoreLevelRestriction) {
        enchantments.forEach((enchantment, level) -> this.enchant(enchantment, level, ignoreLevelRestriction));
        return (B) this;
    }

    /**
     * Enchants the {@link ItemStack} with the specified map where the value
     * is the level of the key's enchantment
     *
     * @param enchantments Enchantments to add
     * @return {@link B}
     * @since 3.1.2
     */
    @NotNull
    @Contract("_ -> this")
    public B enchant(@NotNull final Map<Enchantment, Integer> enchantments) {
        return enchant(enchantments, true);
    }

    /**
     * Disenchants a certain {@link Enchantment} from the {@link ItemStack}
     *
     * @param enchantment The {@link Enchantment} to remove
     * @return {@link B}
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
     * @return {@link B}
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
        meta.setUnbreakable(unbreakable);
        return (B) this;
    }

    /**
     * Sets the item meta of the item
     *
     * @param meta The {@link ItemMeta} to set
     * @return {@link B}
     */
    @NotNull
    @Contract("_ -> this")
    public B meta(ItemMeta meta) {
        this.meta = meta;
        return (B) this;
    }

    /**
     * Returns the {@link ItemMeta} of the item
     *
     * @return The {@link ItemMeta} of the item
     */
    @NotNull
    @Contract(" -> new")
    public ItemMeta meta() {
        return meta;
    }

    /**
     * Returns the {@link ItemStack} of the item
     *
     * @return The {@link ItemStack} of the item
     */
    @NotNull
    @Contract(" -> new")
    public ItemStack asItemStack() {
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * Returns the {@link GuiItem} of the item
     *
     * @param action The {@link GuiClickAction} to set
     * @return The {@link GuiItem} of the item
     */
    @NotNull
    @Contract("_ -> new")
    public GuiItem<Player, ItemStack> asGuiItem(final @NotNull RunnableGuiClickAction<Player> action) {
        return new SimpleGuiItem<>(asItemStack(), action);
    }

    /**
     * Returns the {@link GuiItem} of the item
     *
     * @param action The {@link GuiClickAction} to set
     * @return The {@link GuiItem} of the item
     */
    @NotNull
    @Contract("_ -> new")
    public GuiItem<Player, ItemStack> asGuiItem(final @NotNull GuiClickAction<Player> action) {
        return new SimpleGuiItem<>(asItemStack(), action);
    }

    /**
     * Returns the {@link GuiItem} of the item
     *
     * @return The {@link GuiItem} of the item
     */
    @NotNull
    @Contract(" -> new")
    public GuiItem<Player, ItemStack> asGuiItem() {
        return new SimpleGuiItem<>(asItemStack(), new EmptyGuiClickAction<>());
    }
}
