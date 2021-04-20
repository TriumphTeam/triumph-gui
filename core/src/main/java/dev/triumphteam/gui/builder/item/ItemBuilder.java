package dev.triumphteam.gui.builder.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.triumphteam.gui.components.GuiAction;
import dev.triumphteam.gui.components.util.ItemNBT;
import dev.triumphteam.gui.components.util.SkullUtil;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta meta;

    /**
     * Constructor of the item builder
     *
     * @param itemStack The {@link ItemStack} of the item
     */
    ItemBuilder(@NotNull final ItemStack itemStack) {
        Validate.notNull(itemStack, "Item can't be null!");

        this.itemStack = itemStack;
        meta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
    }

    /**
     * Main method to create {@link ItemBuilder}
     *
     * @param itemStack The {@link ItemStack} you want to edit
     * @return A new {@link ItemBuilder}
     */
    public static ItemBuilder from(@NotNull final ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }

    /**
     * Method for creating a {@link SkullBuilder} which will have PLAYER_HEAD specific methods
     *
     * @return A new {@link SkullBuilder}
     */
    public static SkullBuilder skull() {
        return new SkullBuilder();
    }

    /**
     * Method for creating a {@link SkullBuilder} which will have PLAYER_HEAD specific methods
     *
     * @param itemStack An existing PLAYER_HEAD {@link ItemStack}
     * @return A new {@link SkullBuilder}
     */
    public static SkullBuilder skull(@NotNull final ItemStack itemStack) {
        return new SkullBuilder(itemStack);
    }

    /**
     * Alternative method to create {@link ItemBuilder}
     *
     * @param material The {@link Material} you want to create an item from
     * @return A new {@link ItemBuilder}
     */
    public static ItemBuilder from(@NotNull final Material material) {
        return new ItemBuilder(new ItemStack(material));
    }

    /**
     * Sets the display name of the item using {@link Component}
     *
     * @param component The {@link Component} name
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @Contract("_ -> this")
    public ItemBuilder name(@NotNull final Component component) {
        meta.displayName(component);
        // TODO legacy
        return this;
    }

    /**
     * Sets the amount of items
     *
     * @param amount the amount of items
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    public ItemBuilder amount(final int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    /**
     * Set the lore lines of an item
     *
     * @param lore Lore lines as varargs
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @Contract("_ -> this")
    public ItemBuilder lore(@NotNull final Component... lore) {
        return lore(Arrays.asList(lore));
    }

    /**
     * Set the lore lines of an item
     *
     * @param lore A {@link List} with the lore lines
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @Contract("_ -> this")
    public ItemBuilder lore(@NotNull final List<Component> lore) {
        meta.lore(lore);
        // TODO legacy
        return this;
    }

    /**
     * Consumer for freely adding to the lore
     *
     * @param lore A {@link Consumer} with the {@link List} of lore {@link Component}
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @Contract("_ -> this")
    public ItemBuilder lore(@NotNull final Consumer<List<Component>> lore) {
        List<Component> metaLore = meta.lore();
        if (metaLore == null) {
            metaLore = new ArrayList<>();
        }
        lore.accept(metaLore);
        return lore(metaLore);
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
    @Contract("_, _, _ -> this")
    public ItemBuilder enchant(@NotNull final Enchantment enchantment, final int level, final boolean ignoreLevelRestriction) {
        meta.addEnchant(enchantment, level, ignoreLevelRestriction);
        return this;
    }

    /**
     * Enchants the {@link ItemStack}
     *
     * @param enchantment The {@link Enchantment} to add
     * @param level       The level of the {@link Enchantment}
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @Contract("_, _ -> this")
    public ItemBuilder enchant(@NotNull final Enchantment enchantment, final int level) {
        return addEnchantment(enchantment, level, true);
    }

    /**
     * Enchants the {@link ItemStack}
     *
     * @param enchantment The {@link Enchantment} to add
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @Contract("_ -> this")
    public ItemBuilder enchant(@NotNull final Enchantment enchantment) {
        return addEnchantment(enchantment, 1, true);
    }

    /**
     * Disenchants a certain {@link Enchantment} from the {@link ItemStack}
     *
     * @param enchantment The {@link Enchantment} to remove
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @Contract("_ -> this")
    public ItemBuilder disenchant(@NotNull final Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    /**
     * Add an {@link ItemFlag} to the item
     *
     * @param flags The {@link ItemFlag} to add
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @Contract("_ -> this")
    public ItemBuilder flags(@NotNull final ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    /**
     * Makes the {@link ItemStack} unbreakable
     *
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @Contract(" -> this")
    public ItemBuilder unbreakable() {
        return unbreakable(true);
    }

    /**
     * Sets the item as unbreakable
     *
     * @param unbreakable If should or not be unbreakable
     * @return {@link ItemBuilder}
     */
    @Contract("_ -> this")
    public ItemBuilder unbreakable(boolean unbreakable) {
        // TODO see what to do about 1.12-
        meta.setUnbreakable(unbreakable);
        return this;
    }

    /**
     * Makes the {@link ItemStack} glow
     *
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @Contract(" -> this")
    public ItemBuilder glow() {
        return glow(true);
    }

    /**
     * Adds or removes the {@link ItemStack} glow
     *
     * @param glow Should the item glow
     * @return {@link ItemBuilder}
     */
    @Contract("_ -> this")
    public ItemBuilder glow(boolean glow) {
        if (glow) {
            meta.addEnchant(Enchantment.LURE, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            return this;
        }

        for (final Enchantment enchantment : meta.getEnchants().keySet()) {
            meta.removeEnchant(enchantment);
        }

        return this;
    }

    /**
     * Consumer for applying {@link PersistentDataContainer} to the item
     * This method will only work on versions above 1.14
     *
     * @param consumer The {@link Consumer} with the PDC
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @Contract("_ -> this")
    public ItemBuilder pdc(@NotNull final Consumer<PersistentDataContainer> consumer) {
        consumer.accept(meta.getPersistentDataContainer());
        return this;
    }

    /**
     * Sets the custom model data of the item
     * Added in 1.13
     *
     * @param modelData The custom model data from the resource pack
     * @return {@link ItemBuilder}
     * @since 3.0.0
     */
    @Contract("_ -> this")
    public ItemBuilder model(final int modelData) {
        meta.setCustomModelData(modelData);
        return this;
    }

    /**
     * Sets NBT tag to the {@link ItemStack}
     *
     * @param key   The NBT key
     * @param value The NBT value
     * @return {@link ItemBuilder}
     */
    @Contract("_, _ -> this")
    public ItemBuilder setNbt(@NotNull final String key, @Nullable final String value) {
        itemStack.setItemMeta(meta);
        itemStack = ItemNBT.setNBTTag(itemStack, key, value);
        meta = itemStack.getItemMeta();
        return this;
    }

    /**
     * Removes NBT tag from the {@link ItemStack}
     *
     * @param key The NBT key
     * @return {@link ItemBuilder}
     */
    @Contract("_ -> this")
    public ItemBuilder removeNbt(@NotNull final String key) {
        itemStack.setItemMeta(meta);
        itemStack = ItemNBT.removeNBTTag(itemStack, key);
        meta = itemStack.getItemMeta();
        return this;
    }

    /**
     * Builds the item into {@link ItemStack}
     *
     * @return The fully built {@link ItemStack}
     */
    public ItemStack build() {
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    // TODO this
    public GuiItem asGuiItem() {
        return new GuiItem(build());
    }

    public GuiItem asGuiItem(@NotNull final GuiAction<InventoryClickEvent> action) {
        return new GuiItem(build(), action);
    }

    ItemStack getItemStack() {
        return itemStack;
    }

    void setItemStack(final ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    ItemMeta getMeta() {
        return meta;
    }

    void setMeta(final ItemMeta meta) {
        this.meta = meta;
    }

    // DEPRECATED, TO BE REMOVED METHODS
    // TODO Remove deprecated methods

    /**
     * Set display name of the item
     *
     * @param name the display name of the item
     * @return {@link ItemBuilder}
     * @deprecated In favor of {@link ItemBuilder#name(Component)}, will be removed in 3.0.1
     */
    @Deprecated
    public ItemBuilder setName(@NotNull final String name) {
        meta.setDisplayName(name);
        return this;
    }

    /**
     * Sets the amount of items
     *
     * @param amount the amount of items
     * @return {@link ItemBuilder}
     * @deprecated In favor of {@link ItemBuilder#amount(int)}, nothing changed just the name, will be removed in 3.0.1
     */
    @Deprecated
    public ItemBuilder setAmount(final int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    /**
     * Add lore lines of an item
     *
     * @param lore the lore lines to add
     * @return {@link ItemBuilder}
     * @deprecated In favor of {@link ItemBuilder#lore(Consumer)}, will be removed in 3.0.1
     */
    @Deprecated
    public ItemBuilder addLore(@NotNull final String... lore) {
        return addLore(Arrays.asList(lore));
    }

    /**
     * Set lore lines of an item
     *
     * @param lore A {@link List} with the lore lines to add
     * @return {@link ItemBuilder}
     * @deprecated In favor of {@link ItemBuilder#lore(Consumer)}, will be removed in 3.0.1
     */
    @Deprecated
    public ItemBuilder addLore(@NotNull final List<String> lore) {
        final List<String> newLore;
        if (meta.getLore() == null) {
            newLore = new ArrayList<>();
        } else {
            newLore = meta.getLore();
        }
        newLore.addAll(lore);
        return setLore(newLore);
    }

    /**
     * Set the lore lines of an item
     *
     * @param lore the lore lines to set
     * @return {@link ItemBuilder}
     * @deprecated In favor of {@link ItemBuilder#lore(Component...)}, will be removed in 3.0.1
     */
    @Deprecated
    public ItemBuilder setLore(@NotNull final String... lore) {
        return setLore(Arrays.asList(lore));
    }

    /**
     * Set the lore lines of an item
     *
     * @param lore A {@link List} with the lore lines
     * @return {@link ItemBuilder}
     * @deprecated In favor of {@link ItemBuilder#lore(List)}, will be removed in 3.0.1
     */
    @Deprecated
    public ItemBuilder setLore(@NotNull final List<String> lore) {
        meta.setLore(lore);
        return this;
    }

    /**
     * Add enchantment to an item
     *
     * @param enchantment            the {@link Enchantment} to add
     * @param level                  the level of the {@link Enchantment}
     * @param ignoreLevelRestriction If should or not ignore it
     * @return {@link ItemBuilder}
     * @deprecated In favor of {@link ItemBuilder#enchant(Enchantment, int, boolean)}, nothing changed just the name, will be removed in 3.0.1
     */
    @Deprecated
    public ItemBuilder addEnchantment(@NotNull final Enchantment enchantment, final int level, final boolean ignoreLevelRestriction) {
        meta.addEnchant(enchantment, level, ignoreLevelRestriction);
        return this;
    }

    /**
     * Add enchantment to an item
     *
     * @param enchantment the {@link Enchantment} to add
     * @param level       the level of the {@link Enchantment}
     * @return {@link ItemBuilder}
     * @deprecated In favor of {@link ItemBuilder#enchant(Enchantment, int)}, nothing changed just the name, will be removed in 3.0.1
     */
    @Deprecated
    public ItemBuilder addEnchantment(@NotNull final Enchantment enchantment, final int level) {
        return addEnchantment(enchantment, level, true);
    }

    /**
     * Add enchantment to an item
     *
     * @param enchantment the {@link Enchantment} to add
     * @return {@link ItemBuilder}
     * @deprecated In favor of {@link ItemBuilder#enchant(Enchantment)}, nothing changed just the name, will be removed in 3.0.1
     */
    @Deprecated
    public ItemBuilder addEnchantment(@NotNull final Enchantment enchantment) {
        return addEnchantment(enchantment, 1, true);
    }

    /**
     * Removes a certain {@link Enchantment} from the item
     *
     * @param enchantment The {@link Enchantment} to remove
     * @return {@link ItemBuilder}
     * @deprecated In favor of {@link ItemBuilder#disenchant(Enchantment)}, nothing changed just the name, will be removed in 3.0.1
     */
    @Deprecated
    public ItemBuilder removeEnchantment(@NotNull final Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    /**
     * Add a custom {@link ItemFlag} to the item
     *
     * @param flags the {@link ItemFlag} to add
     * @return {@link ItemBuilder}
     * @deprecated In favor of {@link ItemBuilder#flags(ItemFlag...)}, nothing changed just the name, will be removed in 3.0.1
     */
    @Deprecated
    public ItemBuilder addItemFlags(@NotNull final ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    /**
     * Sets the item as unbreakable
     *
     * @param unbreakable If should or not be unbreakable
     * @return {@link ItemBuilder}
     * @deprecated In favor of {@link ItemBuilder#unbreakable()}, nothing changed just the name, will be removed in 3.0.1
     */
    @Deprecated
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        return unbreakable(unbreakable);
    }

    /**
     * Sets the skull texture
     *
     * @param texture The base64 texture
     * @return {@link ItemBuilder}
     * @deprecated In favor of {@link SkullBuilder#texture(String)}, nothing changed just the name, will be removed in 3.0.1
     */
    @Deprecated
    public ItemBuilder setSkullTexture(@NotNull final String texture) {
        if (itemStack.getType() != SkullUtil.SKULL) return this;

        SkullMeta skullMeta = (SkullMeta) meta;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));
        Field profileField;

        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }

        meta = skullMeta;

        return this;
    }

    /**
     * Sets skull owner via bukkit methods
     *
     * @param player {@link OfflinePlayer} to set skull of
     * @return {@link ItemBuilder}
     * @deprecated In favor of {@link SkullBuilder#owner(OfflinePlayer)}, nothing changed just the name, will be removed in 3.0.1
     */
    @Deprecated
    public ItemBuilder setSkullOwner(@NotNull final OfflinePlayer player) {
        if (itemStack.getType() != SkullUtil.SKULL) return this;

        final SkullMeta skullMeta = (SkullMeta) meta;
        skullMeta.setOwningPlayer(player);

        meta = skullMeta;

        return this;
    }

}
