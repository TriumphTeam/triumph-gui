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

import dev.triumphteam.gui.components.util.SkullUtil;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.enchantment.EnchantmentType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    @NotNull
    @Contract("_ -> new")
    public static ItemBuilder from(@NotNull final ItemStack itemStack) {
        return new ItemBuilder(itemStack);
    }


    /**
     * Alternative method to create {@link ItemBuilder}
     *
     * @param material The {@link Material} you want to create an item from
     * @return A new {@link ItemBuilder}
     */
    @NotNull
    @Contract("_ -> new")
    public static ItemBuilder from(@NotNull final ItemType material) {
        return new ItemBuilder(ItemStack.of(material));
    }

    /**
     * Method for creating a {@link BannerBuilder} which will have BANNER specific methods
     *
     * @return A new {@link BannerBuilder}
     * @since 3.0.1
     */
    @NotNull
    @Contract(" -> new")
    public static BannerBuilder banner() {
        return new BannerBuilder();
    }

    /**
     * Method for creating a {@link BannerBuilder} which will have BANNER specific methods
     *
     * @param itemStack An existing BANNER {@link ItemStack}
     * @return A new {@link BannerBuilder}
     * @throws dev.triumphteam.gui.components.exception.GuiException if the item is not a BANNER
     * @since 3.0.1
     */
    @NotNull
    @Contract("_ -> new")
    public static BannerBuilder banner(@NotNull final ItemStack itemStack) {
        return new BannerBuilder(itemStack);
    }

    /**
     * Method for creating a {@link BookBuilder} which will have {@link Material#WRITABLE_BOOK} /
     * {@link Material#WRITTEN_BOOK} specific methods
     *
     * @param itemStack an existing {@link Material#WRITABLE_BOOK} / {@link Material#WRITTEN_BOOK} {@link ItemStack}
     * @return A new {@link FireworkBuilder}
     * @throws dev.triumphteam.gui.components.exception.GuiException if the item type is not {@link Material#WRITABLE_BOOK}
     *                                                               or {@link Material#WRITTEN_BOOK}
     * @since 3.0.1
     */
    @NotNull
    @Contract("_ -> new")
    public static BookBuilder book(@NotNull final ItemStack itemStack) {
        return new BookBuilder(itemStack);
    }

    /**
     * Method for creating a {@link FireworkBuilder} which will have {@link Material#FIREWORK_ROCKET} specific methods
     *
     * @return A new {@link FireworkBuilder}
     * @since 3.0.1
     */
    @NotNull
    @Contract(" -> new")
    public static FireworkBuilder firework() {
        return new FireworkBuilder(ItemStack.of(ItemTypes.FIREWORKS));
    }

    /**
     * Method for creating a {@link FireworkBuilder} which will have {@link Material#FIREWORK_ROCKET} specific methods
     *
     * @param itemStack an existing {@link Material#FIREWORK_ROCKET} {@link ItemStack}
     * @return A new {@link FireworkBuilder}
     * @throws dev.triumphteam.gui.components.exception.GuiException if the item type is not {@link Material#FIREWORK_ROCKET}
     * @since 3.0.1
     */
    @NotNull
    @Contract("_ -> new")
    public static FireworkBuilder firework(@NotNull final ItemStack itemStack) {
        return new FireworkBuilder(itemStack);
    }

    /**
     * Method for creating a {@link MapBuilder} which will have {@link Material#MAP} specific methods
     *
     * @return A new {@link MapBuilder}
     * @since 3.0.1
     */
    /* TODO
    @NotNull
    @Contract(" -> new")
    public static MapBuilder map() {
        return new MapBuilder();
    }*/

    /**
     * Method for creating a {@link MapBuilder} which will have @link Material#MAP} specific methods
     *
     * @param itemStack An existing {@link Material#MAP} {@link ItemStack}
     * @return A new {@link MapBuilder}
     * @throws dev.triumphteam.gui.components.exception.GuiException if the item type is not {@link Material#MAP}
     * @since 3.0.1
     */
    /* TODO
    @NotNull
    @Contract("_ -> new")
    public static MapBuilder map(@NotNull final ItemStack itemStack) {
        return new MapBuilder(itemStack);
    }*/

    /**
     * Method for creating a {@link SkullBuilder} which will have PLAYER_HEAD specific methods
     *
     * @return A new {@link SkullBuilder}
     */
    @NotNull
    @Contract(" -> new")
    public static SkullBuilder skull() {
        return new SkullBuilder();
    }

    /**
     * Method for creating a {@link SkullBuilder} which will have PLAYER_HEAD specific methods
     *
     * @param itemStack An existing PLAYER_HEAD {@link ItemStack}
     * @return A new {@link SkullBuilder}
     * @throws dev.triumphteam.gui.components.exception.GuiException if the item is not a player head
     */
    @NotNull
    @Contract("_ -> new")
    public static SkullBuilder skull(@NotNull final ItemStack itemStack) {
        return new SkullBuilder(itemStack);
    }

    /**
     * Method for creating a {@link FireworkBuilder} which will have {@link Material#FIREWORK_STAR} specific methods
     *
     * @return A new {@link FireworkBuilder}
     * @since 3.0.1
     */
    @NotNull
    @Contract(" -> new")
    public static FireworkBuilder star() {
        return new FireworkBuilder(ItemStack.of(ItemTypes.FIREWORK_CHARGE));
    }

    /**
     * Method for creating a {@link FireworkBuilder} which will have {@link Material#FIREWORK_STAR} specific methods
     *
     * @param itemStack an existing {@link Material#FIREWORK_STAR} {@link ItemStack}
     * @return A new {@link FireworkBuilder}
     * @throws dev.triumphteam.gui.components.exception.GuiException if the item type is not {@link Material#FIREWORK_STAR}
     * @since 3.0.1
     */
    @NotNull
    @Contract("_ -> new")
    public static FireworkBuilder star(@NotNull final ItemStack itemStack) {
        return new FireworkBuilder(itemStack);
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

        /* TODO
        final SkullMeta skullMeta = (SkullMeta) getMeta();
        final GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));
        final Field profileField;

        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }

        setMeta(skullMeta);*/
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
    public ItemBuilder setSkullOwner(@NotNull final User player) {
        if (getItemStack().getType() != SkullUtil.SKULL) return this;

        this.getMeta().add(Keys.SKIN_UNIQUE_ID, player.getUniqueId());
        return this;
    }

}
