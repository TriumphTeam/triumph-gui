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

import dev.triumphteam.gui.components.exception.GuiException;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * Main ItemBuilder
 */
public class PaperItemBuilder extends BaseItemBuilder<PaperItemBuilder> {

    /**
     * Constructor of the item builder
     *
     * @param itemStack The {@link ItemStack} of the item
     */
    PaperItemBuilder(@NotNull final ItemStack itemStack) {
        super(itemStack, PaperNameLoreHandler.getInstance());
    }

    /**
     * Main method to create {@link PaperItemBuilder}
     *
     * @param itemStack The {@link ItemStack} you want to edit
     * @return A new {@link PaperItemBuilder}
     */
    @NotNull
    @Contract("_ -> new")
    public static PaperItemBuilder from(@NotNull final ItemStack itemStack) {
        return new PaperItemBuilder(itemStack);
    }


    /**
     * Alternative method to create {@link PaperItemBuilder}
     *
     * @param material The {@link Material} you want to create an item from
     * @return A new {@link PaperItemBuilder}
     */
    @NotNull
    @Contract("_ -> new")
    public static PaperItemBuilder from(@NotNull final Material material) {
        return new PaperItemBuilder(new ItemStack(material));
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
        return new BannerBuilder(PaperNameLoreHandler.getInstance());
    }

    /**
     * Method for creating a {@link BannerBuilder} which will have BANNER specific methods
     *
     * @param itemStack An existing BANNER {@link ItemStack}
     * @return A new {@link BannerBuilder}
     * @throws GuiException if the item is not a BANNER
     * @since 3.0.1
     */
    @NotNull
    @Contract("_ -> new")
    public static BannerBuilder banner(@NotNull final ItemStack itemStack) {
        return new BannerBuilder(itemStack, PaperNameLoreHandler.getInstance());
    }

    /**
     * Method for creating a {@link BookBuilder} which will have {@link Material#WRITABLE_BOOK} /
     * {@link Material#WRITTEN_BOOK} specific methods
     *
     * @param itemStack an existing {@link Material#WRITABLE_BOOK} / {@link Material#WRITTEN_BOOK} {@link ItemStack}
     * @return A new {@link FireworkBuilder}
     * @throws GuiException if the item type is not {@link Material#WRITABLE_BOOK}
     *                      or {@link Material#WRITTEN_BOOK}
     * @since 3.0.1
     */
    @NotNull
    @Contract("_ -> new")
    public static BookBuilder book(@NotNull final ItemStack itemStack) {
        return new BookBuilder(itemStack, PaperNameLoreHandler.getInstance());
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
        return new FireworkBuilder(new ItemStack(Material.FIREWORK_ROCKET), PaperNameLoreHandler.getInstance());
    }

    /**
     * Method for creating a {@link FireworkBuilder} which will have {@link Material#FIREWORK_ROCKET} specific methods
     *
     * @param itemStack an existing {@link Material#FIREWORK_ROCKET} {@link ItemStack}
     * @return A new {@link FireworkBuilder}
     * @throws GuiException if the item type is not {@link Material#FIREWORK_ROCKET}
     * @since 3.0.1
     */
    @NotNull
    @Contract("_ -> new")
    public static FireworkBuilder firework(@NotNull final ItemStack itemStack) {
        return new FireworkBuilder(itemStack, PaperNameLoreHandler.getInstance());
    }

    /**
     * Method for creating a {@link MapBuilder} which will have {@link Material#MAP} specific methods
     *
     * @return A new {@link MapBuilder}
     * @since 3.0.1
     */
    @NotNull
    @Contract(" -> new")
    public static MapBuilder map() {
        return new MapBuilder(PaperNameLoreHandler.getInstance());
    }

    /**
     * Method for creating a {@link MapBuilder} which will have @link Material#MAP} specific methods
     *
     * @param itemStack An existing {@link Material#MAP} {@link ItemStack}
     * @return A new {@link MapBuilder}
     * @throws GuiException if the item type is not {@link Material#MAP}
     * @since 3.0.1
     */
    @NotNull
    @Contract("_ -> new")
    public static MapBuilder map(@NotNull final ItemStack itemStack) {
        return new MapBuilder(itemStack, PaperNameLoreHandler.getInstance());
    }

    /**
     * Method for creating a {@link SkullBuilder} which will have PLAYER_HEAD specific methods
     *
     * @return A new {@link SkullBuilder}
     */
    @NotNull
    @Contract(" -> new")
    public static SkullBuilder skull() {
        return new SkullBuilder(PaperNameLoreHandler.getInstance());
    }

    /**
     * Method for creating a {@link SkullBuilder} which will have PLAYER_HEAD specific methods
     *
     * @param itemStack An existing PLAYER_HEAD {@link ItemStack}
     * @return A new {@link SkullBuilder}
     * @throws GuiException if the item is not a player head
     */
    @NotNull
    @Contract("_ -> new")
    public static SkullBuilder skull(@NotNull final ItemStack itemStack) {
        return new SkullBuilder(itemStack, PaperNameLoreHandler.getInstance());
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
        return new FireworkBuilder(new ItemStack(Material.FIREWORK_STAR), PaperNameLoreHandler.getInstance());
    }

    /**
     * Method for creating a {@link FireworkBuilder} which will have {@link Material#FIREWORK_STAR} specific methods
     *
     * @param itemStack an existing {@link Material#FIREWORK_STAR} {@link ItemStack}
     * @return A new {@link FireworkBuilder}
     * @throws GuiException if the item type is not {@link Material#FIREWORK_STAR}
     * @since 3.0.1
     */
    @NotNull
    @Contract("_ -> new")
    public static FireworkBuilder star(@NotNull final ItemStack itemStack) {
        return new FireworkBuilder(itemStack, PaperNameLoreHandler.getInstance());
    }
}
