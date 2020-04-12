package me.mattstudios.mfgui.gui.components;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public final class ItemBuilder {

    private final ItemStack itemStack;
    private ItemMeta meta;

    /**
     * Constructor of the item builder
     *
     * @param itemStack The ItemStack of the item
     */
    public ItemBuilder(final ItemStack itemStack) {
        Validate.notNull(itemStack, "Item can't be null!");

        this.itemStack = itemStack;
        meta = itemStack.hasItemMeta() ? itemStack.getItemMeta() : Bukkit.getItemFactory().getItemMeta(itemStack.getType());
    }

    /**
     * Secondary constructor with only Material
     *
     * @param material The material of the ItemStack
     */
    public ItemBuilder(final Material material) {
        this(new ItemStack(material));
    }

    /**
     * Builds the item into ItemStack
     *
     * @return The fully built item
     */
    public ItemStack build() {
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * Set display name of the item
     *
     * @param name the display name of the item
     * @return The ItemBuilder
     */
    public ItemBuilder setName(@NotNull final String name) {
        meta.setDisplayName(name);
        return this;
    }

    /**
     * Sets the amount of items
     *
     * @param amount the amount of items
     * @return The ItemBuilder
     */
    public ItemBuilder setAmount(final int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    /**
     * Set the lore lines of an item
     *
     * @param lore the lore lines to set
     * @return The ItemBuilder
     */
    public ItemBuilder setLore(@NotNull final String... lore) {
        meta.setLore(Arrays.asList(lore));
        return this;
    }

    /**
     * Set the lore lines of an item
     *
     * @param lore the lore lines to set
     * @return The ItemBuilder
     */
    public ItemBuilder setLore(@NotNull final List<String> lore) {
        meta.setLore(lore);
        return this;
    }

    /**
     * Add enchantments to an item
     *
     * @param enchantment            the enchantment to add
     * @param level                  the level of the enchantment
     * @param ignoreLevelRestriction If should or not ignore it
     * @return The ItemBuilder
     */
    public ItemBuilder addEnchantment(@NotNull final Enchantment enchantment, final int level, final boolean ignoreLevelRestriction) {
        meta.addEnchant(enchantment, level, ignoreLevelRestriction);
        return this;
    }

    /**
     * Add enchantments to an item
     *
     * @param enchantment the enchantment to add
     * @param level       the level of the enchantment
     * @return The ItemBuilder
     */
    public ItemBuilder addEnchantment(@NotNull final Enchantment enchantment, final int level) {
        return addEnchantment(enchantment, level, true);
    }

    /**
     * Add enchantments to an item
     *
     * @param enchantment the enchantment to add
     * @return The ItemBuilder
     */
    public ItemBuilder addEnchantment(@NotNull final Enchantment enchantment) {
        return addEnchantment(enchantment, 1, true);
    }

    /**
     * Removes a certain enchantment from the item
     *
     * @param enchantment The enchantment to remove
     * @return The ItemBuilder
     */
    public ItemBuilder removeEnchantment(@NotNull final Enchantment enchantment) {
        itemStack.removeEnchantment(enchantment);
        return this;
    }

    /**
     * Add a custom item flag to the item
     *
     * @param flags the flags to add
     * @return The ItemBuilder
     */
    public ItemBuilder addItemFlags(@NotNull final ItemFlag... flags) {
        meta.addItemFlags(flags);
        return this;
    }

    /**
     * Sets the item as unbreakable
     *
     * @param unbreakable If should or not be unbreakable
     * @return The ItemBuilder
     */
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        meta.setUnbreakable(unbreakable);
        return this;
    }

    /**
     * Makes the Item glow
     *
     * @param boolean Should the item glow
     * @return The ItemBuilder
     */
    public ItemBuilder glow(boolean bool) {
        if (bool) {
            meta.addEnchant(Enchantment.LURE, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
           
        } else {
            ItemStack stack = new ItemStack(Material.STONE);
            ItemMeta meta = stack.getItemMeta();
            for (Enchantment enchant : stack.getEnchantments().keySet()) {
                meta.removeEnchant(enchant);
            }
            stack.setItemMeta(meta);
        }

        return this;
    }

    /**
     * Sets the skull texture
     * @param texture The base64 texture
     * @return The ItemBuilder
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
     * @param player OfflinePlayer to set skull of
     * @return The ItemBuilder
     */
    public ItemBuilder setSkullOwner(@NotNull final OfflinePlayer player) {
        if (itemStack.getType() != XMaterial.PLAYER_HEAD.parseMaterial()) return this;

        final SkullMeta skullMeta = (SkullMeta) meta;
        skullMeta.setOwningPlayer(player);

        meta = skullMeta;

        return this;
    }

}
