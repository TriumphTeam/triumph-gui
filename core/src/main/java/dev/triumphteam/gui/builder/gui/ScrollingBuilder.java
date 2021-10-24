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
package dev.triumphteam.gui.builder.gui;

import dev.triumphteam.gui.components.ScrollType;
import dev.triumphteam.gui.components.util.Legacy;
import dev.triumphteam.gui.guis.ScrollingGui;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * The simple GUI builder is used for creating a {@link ScrollingGui} that uses {@link Component} for title
 * TODO This class needs more work to remove the redundant pageSize since it's the same as the paginated builder
 */
public final class ScrollingBuilder extends BaseGuiBuilder<ScrollingGui, ScrollingBuilder> {

    private ScrollType scrollType;
    private int pageSize = -1;

    /**
     * Main constructor
     *
     * @param scrollType The {@link ScrollType} to default to
     */
    public ScrollingBuilder(@NotNull final ScrollType scrollType) {
        this.scrollType = scrollType;
    }

    /**
     * Sets the {@link ScrollType} to be used
     *
     * @param scrollType Either horizontal or vertical scrolling
     * @return The current builder
     */
    @NotNull
    @Contract("_ -> this")
    public ScrollingBuilder scrollType(@NotNull final ScrollType scrollType) {
        this.scrollType = scrollType;
        return this;
    }

    /**
     * Sets the desirable page size, most of the times this isn't needed
     *
     * @param pageSize The amount of free slots that page items should occupy
     * @return The current builder
     */
    @NotNull
    @Contract("_ -> this")
    public ScrollingBuilder pageSize(final int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    /**
     * Creates a new {@link ScrollingGui}
     *
     * @return A new {@link ScrollingGui}
     */
    @NotNull
    @Override
    @Contract(" -> new")
    public ScrollingGui create() {
        final ScrollingGui gui = new ScrollingGui(getRows(), pageSize, Legacy.SERIALIZER.serialize(getTitle()), scrollType, getModifiers());

        final Consumer<ScrollingGui> consumer = getConsumer();
        if (consumer != null) consumer.accept(gui);

        return gui;
    }

}
