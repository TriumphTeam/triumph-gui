package dev.triumphteam.gui.builder.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.triumphteam.gui.components.util.SkullUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Main ItemBuilder
 */
public class ItemBuilder extends BaseItemBuilder<ItemBuilder> {

    /**
     * Constructor of the item builder
     *
     * @param itemStack The {@link ItemStack} of the item
     */
    ItemBuilder(@NotNull final ItemStack itemStack) {
        super(itemStack);
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
        return new ItemBuilder(new ItemStack(material));
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
        getMeta().setDisplayName(name);
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
        getItemStack().setAmount(amount);
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
        final ItemMeta meta = getMeta();
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
        getMeta().setLore(lore);
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
        getMeta().addEnchant(enchantment, level, ignoreLevelRestriction);
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
        getItemStack().removeEnchantment(enchantment);
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
        getMeta().addItemFlags(flags);
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
        if (getItemStack().getType() != SkullUtil.SKULL) return this;

        SkullMeta skullMeta = (SkullMeta) getMeta();
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

        setMeta(skullMeta);
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
        if (getItemStack().getType() != SkullUtil.SKULL) return this;

        final SkullMeta skullMeta = (SkullMeta) getMeta();
        skullMeta.setOwningPlayer(player);

        setMeta(skullMeta);
        return this;
    }

}
