/**
 * MIT License
 * <p>
 * Copyright (c) 2021 TriumphTeam
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.gui.builder.gui;

import dev.triumphteam.gui.components.GuiContainer;
import dev.triumphteam.gui.components.GuiType;
import dev.triumphteam.gui.components.InventoryProvider;
import dev.triumphteam.gui.components.util.Legacy;
import dev.triumphteam.gui.guis.Gui;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * The simple GUI builder is used for creating a {@link Gui}
 */
public final class TypedGuiBuilder extends BaseGuiBuilder<Gui, TypedGuiBuilder> {

    private GuiType guiType;
    private InventoryProvider.Typed inventoryProvider =
        (title, owner, type) -> Bukkit.createInventory(owner, type, Legacy.SERIALIZER.serialize(title));

    /**
     * Main constructor
     *
     * @param guiType The {@link GuiType} to default to
     */
    public TypedGuiBuilder(final @NotNull GuiType guiType) {
        this.guiType = guiType;
    }

    public TypedGuiBuilder(final @NotNull GuiType guiType, final @NotNull ChestGuiBuilder builder) {
        this.guiType = guiType;
        consumeBuilder(builder);
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
    public TypedGuiBuilder type(final @NotNull GuiType guiType) {
        this.guiType = guiType;
        return this;
    }

    @NotNull
    @Contract("_ -> this")
    public TypedGuiBuilder inventory(@NotNull final InventoryProvider.Typed inventoryProvider) {
        this.inventoryProvider = inventoryProvider;
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
        final Gui gui = new Gui(new GuiContainer.Typed(getTitle(), inventoryProvider, guiType), getModifiers());
        final Consumer<Gui> consumer = getConsumer();
        if (consumer != null) consumer.accept(gui);
        return gui;
    }

}
