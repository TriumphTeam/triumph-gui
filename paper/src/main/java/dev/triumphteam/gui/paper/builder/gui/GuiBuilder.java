package dev.triumphteam.gui.paper.builder.gui;

import dev.triumphteam.gui.builder.BaseGuiBuilder;
import dev.triumphteam.gui.container.type.GuiContainerType;
import dev.triumphteam.gui.paper.PaperGui;
import dev.triumphteam.gui.paper.PaperGuiSettings;
import dev.triumphteam.gui.paper.container.type.ChestContainerType;
import dev.triumphteam.gui.paper.container.type.PaperContainerType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class GuiBuilder extends BaseGuiBuilder<GuiBuilder, Player, PaperGui, ItemStack> {

    public GuiBuilder(final @NotNull GuiContainerType containerType) {
        super(PaperGuiSettings.get(), containerType);
    }

    public static @NotNull GuiBuilder gui(final @NotNull GuiContainerType type) {
        return new GuiBuilder(type);
    }

    public static @NotNull GuiBuilder gui(final int rows) {
        return gui(ChestContainerType.of(rows));
    }

    @Override
    public PaperGui build() {
        if (!(getContainerType() instanceof final PaperContainerType paperContainerType)) {
            throw new IllegalArgumentException("Container type provided is not supported on Paper platform!");
        }

        return new PaperGui(
            getTitle(),
            getComponents(),
            paperContainerType,
            getComponentRenderer(),
            getClickHandler(),
            getSpamPreventionDuration()
        );
    }
}
