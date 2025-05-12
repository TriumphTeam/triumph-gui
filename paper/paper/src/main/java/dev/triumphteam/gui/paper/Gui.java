/**
 * MIT License
 *
 * Copyright (c) 2024 TriumphTeam
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
package dev.triumphteam.gui.paper;

import dev.triumphteam.gui.BaseGui;
import dev.triumphteam.gui.GuiView;
import dev.triumphteam.gui.actions.GuiCloseAction;
import dev.triumphteam.gui.click.handler.ClickHandler;
import dev.triumphteam.gui.component.GuiComponent;
import dev.triumphteam.gui.component.renderer.GuiComponentRenderer;
import dev.triumphteam.gui.paper.builder.gui.PaperGuiBuilder;
import dev.triumphteam.gui.paper.container.type.ChestContainerType;
import dev.triumphteam.gui.paper.container.type.HopperContainerType;
import dev.triumphteam.gui.paper.container.type.PaperContainerType;
import dev.triumphteam.gui.title.GuiTitle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * The GUI implementation for Paper servers.
 */
public final class Gui implements BaseGui<Player> {

    private static final Plugin plugin = PaperGuiSettings.get().getPlugin();

    static {
        PaperGuiListener.register(plugin);
    }

    private final GuiTitle title;
    private final List<GuiComponent<Player, ItemStack>> components;
    private final List<GuiCloseAction> closeActions;
    private final PaperContainerType containerType;
    private final GuiComponentRenderer<Player, ItemStack> componentRenderer;
    private final ClickHandler<Player> clickHandler;
    private final long spamPreventionDuration;
    private final boolean usePlayerInventory;

    public Gui(
            final @NotNull GuiTitle title,
            final @NotNull List<GuiComponent<Player, ItemStack>> components,
            final @NotNull List<GuiCloseAction> closeActions,
            final @NotNull PaperContainerType containerType,
            final @NotNull GuiComponentRenderer<Player, ItemStack> componentRenderer,
            final @NotNull ClickHandler<Player> clickHandler,
            final long spamPreventionDuration,
            final boolean usePlayerInventory
    ) {
        this.title = title;
        this.components = components;
        this.closeActions = closeActions;
        this.containerType = containerType;
        this.componentRenderer = componentRenderer;
        this.clickHandler = clickHandler;
        this.spamPreventionDuration = spamPreventionDuration;
        this.usePlayerInventory = usePlayerInventory;
    }

    /**
     * Create a new {@link PaperGuiBuilder} to create a new {@link Gui}.
     *
     * @param type The {@link PaperContainerType} to be used.
     * @return A new {@link PaperGuiBuilder}.
     */
    @Contract("_ -> new")
    public static PaperGuiBuilder of(final @NotNull PaperContainerType type) {
        return new PaperGuiBuilder(type);
    }

    /**
     * Create a new {@link PaperGuiBuilder} to create a new {@link Gui}.
     * This factory will default to using a {@link ChestContainerType}.
     *
     * @param rows The rows of the {@link ChestContainerType}.
     * @return A new {@link PaperGuiBuilder}.
     */
    @Contract("_ -> new")
    public static PaperGuiBuilder of(final int rows) {
        return new PaperGuiBuilder(PaperContainerType.chest(rows));
    }

    /**
     * Create a new {@link PaperGuiBuilder} to create a new {@link Gui} using a {@link HopperContainerType}.
     *
     * @return A new {@link PaperGuiBuilder}.
     */
    @Contract(" -> new")
    public static PaperGuiBuilder hopper() {
        return new PaperGuiBuilder(PaperContainerType.hopper());
    }

    @Override
    public @NotNull GuiView open(final @NotNull Player player) {
        final var view = new PaperGuiView(
                player,
                title,
                containerType,
                components,
                closeActions,
                componentRenderer,
                clickHandler,
                spamPreventionDuration,
                usePlayerInventory
        );

        view.open();
        return view;
    }
}
