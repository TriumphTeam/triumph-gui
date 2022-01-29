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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.meta.PatternLayer;
import org.spongepowered.api.data.type.BannerPatternShape;
import org.spongepowered.api.data.type.DyeColor;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Item builder for banners only
 *
 * @author GabyTM <a href="https://github.com/iGabyTM">https://github.com/iGabyTM</a>
 * @since 3.0.1
 */
@SuppressWarnings("unused")
public final class BannerBuilder extends BaseItemBuilder<BannerBuilder> {

    private static final ItemType DEFAULT_BANNER;
    private static final Set<ItemType> BANNERS;

    static {
        if (VersionHelper.IS_ITEM_LEGACY) {
            DEFAULT_BANNER = ItemTypes.BANNER;
            BANNERS = new HashSet<>(Arrays.asList(ItemTypes.BANNER));
        } else {
            DEFAULT_BANNER = ItemTypes.BANNER;
            BANNERS = new HashSet<>(Arrays.asList(ItemTypes.BANNER));
        }
    }

    BannerBuilder() {
        super(ItemStack.builder().itemType(DEFAULT_BANNER).build());
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
        this.getMeta().add(Keys.DYE_COLOR, color);
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
    public BannerBuilder pattern(@NotNull final DyeColor color, @NotNull final PatternLayer pattern) {
        final List<PatternLayer> patterns = new ArrayList<>();
        patterns.add(PatternLayer.of(pattern.getShape(), color));
        this.getMeta().add(Keys.BANNER_PATTERNS, patterns);
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
    public BannerBuilder pattern(@NotNull final PatternLayer... pattern) {
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
    public BannerBuilder pattern(@NotNull final List<PatternLayer> patterns) {
        this.getMeta().add(Keys.BANNER_PATTERNS, patterns);
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
    public BannerBuilder pattern(final int index, @NotNull final DyeColor color, @NotNull final BannerPatternShape pattern) {
        return pattern(index, PatternLayer.of(pattern, color));
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
    public BannerBuilder pattern(final int index, @NotNull final PatternLayer pattern) {
        this.getMeta().add(Keys.BANNER_PATTERNS, new ArrayList<>(Arrays.asList(pattern)));
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
    public BannerBuilder setPatterns(@NotNull List<@NotNull PatternLayer> patterns) {
        this.getMeta().add(Keys.BANNER_PATTERNS, patterns);
        return this;
    }

    // TODO add shield()

}
