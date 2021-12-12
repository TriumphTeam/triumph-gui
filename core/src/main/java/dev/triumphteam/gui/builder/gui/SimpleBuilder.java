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

import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.components.util.Legacy;
import dev.triumphteam.gui.guis.Gui;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * The simple GUI builder is used for creating a {@link Gui}
 */
public final class SimpleBuilder extends BaseGuiBuilder<Gui, SimpleBuilder> {

    private GuiType guiType;

    /**
     * Main constructor
     *
     * @param guiType The {@link GuiType} to default to
     */
    public SimpleBuilder(@NotNull final GuiType guiType) {
        this.guiType = guiType;
    }

    /**
     * Sets the {@link GuiType} to use on the GUI
     * This method is unique to the simple GUI
     *
     * @param guiType The {@link GuiType}
     * @return The current builder
     */
    @NotNull
    @Contract("_ -> this")
    public SimpleBuilder type(@NotNull final GuiType guiType) {
        this.guiType = guiType;
        return this;
    }

    /**
     * Creates a new {@link Gui}
     *
     * @return A new {@link Gui}
     */
    @NotNull
    @Override
    @Contract(" -> new")
    public Gui create() {
        final Gui gui;
        final String title = Legacy.SERIALIZER.serialize(getTitle());
        if (guiType == null || guiType == GuiType.CHEST) {
            gui = new Gui(getRows(), title, getModifiers());
        } else {
            gui = new Gui(guiType, title, getModifiers());
        }

        final Consumer<Gui> consumer = getConsumer();
        if (consumer != null) consumer.accept(gui);

        return gui;
    }

}
