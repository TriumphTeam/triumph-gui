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
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * Item builder for {@link Material#MAP} only
 *
 * @author GabyTM <a href="https://github.com/iGabyTM">https://github.com/iGabyTM</a>
 * @since 3.0.1
 */
public class MapBuilder extends BaseItemBuilder<MapBuilder> implements ColorableBuilder<MapBuilder> {

    private static final Material MAP = Material.MAP;

    MapBuilder() {
        super(new ItemStack(MAP));
    }

    MapBuilder(@NotNull ItemStack itemStack) {
        super(itemStack);
        if (itemStack.getType() != MAP) {
            throw new GuiException("MapBuilder requires the material to be a MAP!");
        }
    }

    /**
     * Sets the map color. A custom map color will alter the display of the map
     * in an inventory slot.
     *
     * @param color the color to set
     * @return {@link MapBuilder}
     * @since 3.0.1
     */
    @NotNull
    @Override
    @Contract("_ -> this")
    public MapBuilder color(@Nullable final Color color) {
        final MapMeta mapMeta = (MapMeta) getMeta();

        mapMeta.setColor(color);
        setMeta(mapMeta);
        return this;
    }

    /**
     * Sets the location name. A custom map color will alter the display of the
     * map in an inventory slot.
     *
     * @param name the name to set
     * @return {@link MapMeta}
     * @since 3.0.1
     */
    @NotNull
    @Contract("_ -> this")
    public MapBuilder locationName(@Nullable final String name) {
        final MapMeta mapMeta = (MapMeta) getMeta();

        mapMeta.setLocationName(name);
        setMeta(mapMeta);
        return this;
    }

    /**
     * Sets if this map is scaling or not.
     *
     * @param scaling true to scale
     * @return {@link MapMeta}
     * @since 3.0.1
     */
    @NotNull
    @Contract("_ -> this")
    public MapBuilder scaling(final boolean scaling) {
        final MapMeta mapMeta = (MapMeta) getMeta();

        mapMeta.setScaling(scaling);
        setMeta(mapMeta);
        return this;
    }

    /**
     * Sets the associated map. This is used to determine what map is displayed.
     *
     * <p>
     * The implementation <b>may</b> allow null to clear the associated map, but
     * this is not required and is liable to generate a new (undefined) map when
     * the item is first used.
     *
     * @param view the map to set
     * @return {@link MapBuilder}
     * @since 3.0.1
     */
    @NotNull
    @Contract("_ -> this")
    public MapBuilder view(@NotNull final MapView view) {
        final MapMeta mapMeta = (MapMeta) getMeta();

        mapMeta.setMapView(view);
        setMeta(mapMeta);
        return this;
    }

}
