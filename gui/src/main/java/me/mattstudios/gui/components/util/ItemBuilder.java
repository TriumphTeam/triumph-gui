package me.mattstudios.gui.components.util;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.mattstudios.gui.components.GuiAction;
import me.mattstudios.gui.components.exception.GuiException;
import me.mattstudios.gui.components.xseries.XMaterial;
import me.mattstudios.gui.guis.GuiItem;
import me.mattstudios.util.ServerVersion;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public final class ItemBuilder {

    private ItemStack itemStack;
    private ItemMeta meta;

    /**
     * Constructor of the item builder
     *
     * @param itemStack The {@link ItemStack} of the item
     * @deprecated Use {@link ItemBuilder#from(ItemStack)} instead, it's more idiomatic for a builder
     */
    @Deprecated
    public ItemBuilder(@NotNull final ItemStack itemStack) {
        Validate.notNull(itemStack, "Item can't be null!");

        this.itemStack = itemStack;
        meta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
    }

    /**
     * Alternative constructor with {@link Material}
     *
     * @param material The {@link Material} of the {@link ItemStack}
     * @deprecated Use {@link ItemBuilder#from(Material)} instead, it's more idiomatic for a builder
     */
    @Deprecated
    public ItemBuilder(final Material material) {
        this(new ItemStack(material));
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
     * Alternative method to create {@link ItemBuilder}
     *
     * @param material The {@link Material} you want to create an item from
     * @return A new {@link ItemBuilder}
     */
    public static ItemBuilder from(@NotNull final Material material) {
        return new ItemBuilder(material);
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

    public GuiItem asGuiItem() {
        return new GuiItem(build());
    }

    public GuiItem asGuiItem(@NotNull final GuiAction<InventoryClickEvent> action) {
        return new GuiItem(build(), action);
    }

    /**
     * Set display name of the item
     *
     * @param name the display name of the item
     * @return {@link ItemBuilder}
     */
    public ItemBuilder setName(@NotNull final String name) {
        meta.setDisplayName(name);
        return this;
    }

    /**
     * Sets the amount of items
     *
     * @param amount the amount of items
     * @return {@link ItemBuilder}
     */
    public ItemBuilder setAmount(final int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    /**
     * Add lore lines of an item
     *
     * @param lore the lore lines to add
     * @return {@link ItemBuilder}
     */
    public ItemBuilder addLore(@NotNull final String... lore) {
        return addLore(Arrays.asList(lore));
    }

    /**
     * Set lore lines of an item
     *
     * @param lore A {@link List} with the lore lines to add
     * @return {@link ItemBuilder}
     */
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
     */
    public ItemBuilder setLore(@NotNull final String... lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    /**
     * Set the lore lines of an item
     *
     * @param lore A {@link List} with the lore lines
     * @return {@link ItemBuilder}
     */
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
     */
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
     */
    public ItemBuilder addEnchantment(@NotNull final Enchantment enchantment, final int level) {
        return addEnchantment(enchantment, level, true);
    }

    /**
     * Add enchantment to an item
     *
     * @param enchantment the {@link Enchantment} to add
     * @return {@link ItemBuilder}
     */
    public ItemBuilder addEnchantment(@NotNull final Enchantment enchantment) {
        return addEnchantment(enchantment, 1, true);
    }

    /**
     * Removes a certain {@link Enchantment} from the item
     *
     * @param enchantment The {@link Enchantment} to remove
     * @return {@link ItemBuilder}
     */
    public ItemBuilder removeEnchantment(@NotNull final Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    /**
     * Add a custom {@link ItemFlag} to the item
     *
     * @param flags the {@link ItemFlag} to add
     * @return {@link ItemBuilder}
     */
    public ItemBuilder addItemFlags(@NotNull final ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    /**
     * Sets the item as unbreakable
     *
     * @param unbreakable If should or not be unbreakable
     * @return {@link ItemBuilder}
     */
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        if (ServerVersion.CURRENT_VERSION.isOlderThan(ServerVersion.V1_12_R1)) {
            throw new GuiException("setUnbreakable is not supported on versions below 1.12!");
        }

        meta.setUnbreakable(unbreakable);
        return this;
    }

    /**
     * Makes the Item glow
     *
     * @param glow Should the item glow
     * @return {@link ItemBuilder}
     */
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
     * Sets the skull texture
     *
     * @param texture The base64 texture
     * @return {@link ItemBuilder}
     */
    public ItemBuilder setSkullTexture(@NotNull final String texture) {
        if (itemStack.getType() != XMaterial.PLAYER_HEAD.parseMaterial()) return this;

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
     */
    public ItemBuilder setSkullOwner(@NotNull final OfflinePlayer player) {
        if (itemStack.getType() != XMaterial.PLAYER_HEAD.parseMaterial()) return this;

        final SkullMeta skullMeta = (SkullMeta) meta;
        skullMeta.setOwningPlayer(player);

        meta = skullMeta;

        return this;
    }

    /**
     * Sets NBT tag to the {@link ItemStack}
     *
     * @param key   The NBT key
     * @param value The NBT value
     * @return {@link ItemBuilder}
     */
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
    public ItemBuilder removeNbt(@NotNull final String key) {
        itemStack.setItemMeta(meta);
        itemStack = ItemNBT.removeNBTTag(itemStack, key);
        meta = itemStack.getItemMeta();
        return this;
    }

}
