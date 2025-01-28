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
import dev.triumphteam.gui.components.util.VersionHelper;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;

/**
 * Item builder for banners only
 *
 * @author GabyTM <a href="https://github.com/iGabyTM">https://github.com/iGabyTM</a>
 * @since 3.0.1
 */
@SuppressWarnings("unused")
public final class BannerBuilder extends BaseItemBuilder<BannerBuilder> {

    private static final Material DEFAULT_BANNER;
    private static final EnumSet<Material> BANNERS;

    static {
        if (VersionHelper.IS_ITEM_LEGACY) {
            DEFAULT_BANNER = Material.valueOf("BANNER");
            BANNERS = EnumSet.of(Material.valueOf("BANNER"));
        } else {
            DEFAULT_BANNER = Material.WHITE_BANNER;
            BANNERS = EnumSet.copyOf(Tag.BANNERS.getValues());
        }
    }

    BannerBuilder() {
        super(new ItemStack(DEFAULT_BANNER));
    }

    BannerBuilder(@NotNull ItemStack itemStack) {
        super(itemStack);
        if (!BANNERS.contains(itemStack.getType())) {
            throw new GuiException("BannerBuilder requires the material to be a banner!");
        }
    }

    /**
     * Sets the base color for this banner
     *
     * @param color the base color
     * @return {@link BannerBuilder}
     * @since 3.0.1
     */
    @NotNull
    @Contract("_ -> this")
    public BannerBuilder baseColor(@NotNull final DyeColor color) {
/*        final BannerMeta bannerMeta = (BannerMeta) getMeta();
        bannerMeta.setBaseColor(color);
        setMeta(bannerMeta);*/
        return this;
    }

    /**
     * Adds a new pattern on top of the existing patterns
     *
     * @param color   the pattern color
     * @param pattern the pattern type
     * @return {@link BannerBuilder}
     * @since 3.0.1
     */
    @NotNull
    @Contract("_, _ -> this")
    public BannerBuilder pattern(@NotNull final DyeColor color, @NotNull final PatternType pattern) {
        final BannerMeta bannerMeta = (BannerMeta) getMeta();

        bannerMeta.addPattern(new Pattern(color, pattern));
        setMeta(bannerMeta);
        return this;
    }

    /**
     * Adds new patterns on top of the existing patterns
     *
     * @param pattern the patterns
     * @return {@link BannerBuilder}
     * @since 3.0.1
     */
    @NotNull
    @Contract("_ -> this")
    public BannerBuilder pattern(@NotNull final Pattern... pattern) {
        return pattern(Arrays.asList(pattern));
    }

    /**
     * Adds new patterns on top of the existing patterns
     *
     * @param patterns the patterns
     * @return {@link BannerBuilder}
     * @since 3.0.1
     */
    @NotNull
    @Contract("_ -> this")
    public BannerBuilder pattern(@NotNull final List<Pattern> patterns) {
        final BannerMeta bannerMeta = (BannerMeta) getMeta();

        for (final Pattern it : patterns) {
            bannerMeta.addPattern(it);
        }

        setMeta(bannerMeta);
        return this;
    }

    /**
     * Sets the pattern at the specified index
     *
     * @param index   the index
     * @param color   the pattern color
     * @param pattern the pattern type
     * @return {@link BannerBuilder}
     * @throws IndexOutOfBoundsException when index is not in [0, {@link BannerMeta#numberOfPatterns()}) range
     * @since 3.0.1
     */
    @NotNull
    @Contract("_, _, _ -> this")
    public BannerBuilder pattern(final int index, @NotNull final DyeColor color, @NotNull final PatternType pattern) {
        return pattern(index, new Pattern(color, pattern));
    }

    /**
     * Sets the pattern at the specified index
     *
     * @param index   the index
     * @param pattern the new pattern
     * @return {@link BannerBuilder}
     * @throws IndexOutOfBoundsException when index is not in [0, {@link BannerMeta#numberOfPatterns()}) range
     * @since 3.0.1
     */
    @NotNull
    @Contract("_, _ -> this")
    public BannerBuilder pattern(final int index, @NotNull final Pattern pattern) {
        final BannerMeta bannerMeta = (BannerMeta) getMeta();

        bannerMeta.setPattern(index, pattern);
        setMeta(bannerMeta);
        return this;
    }

    /**
     * Sets the patterns used on this banner
     *
     * @param patterns the new list of patterns
     * @return {@link BannerBuilder}
     * @since 3.0.1
     */
    @NotNull
    @Contract("_ -> this")
    public BannerBuilder setPatterns(@NotNull List<@NotNull Pattern> patterns) {
        final BannerMeta bannerMeta = (BannerMeta) getMeta();

        bannerMeta.setPatterns(patterns);
        setMeta(bannerMeta);
        return this;
    }

    // TODO add shield()

}
