package dev.triumphteam.gui.paper.builder.gui;

import dev.triumphteam.gui.builder.BaseGuiBuilder;
import dev.triumphteam.gui.container.type.GuiContainerType;
import dev.triumphteam.gui.paper.Gui;
import dev.triumphteam.gui.paper.PaperGuiSettings;
import dev.triumphteam.gui.paper.container.type.PaperContainerType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class GuiBuilder extends BaseGuiBuilder<GuiBuilder, Player, Gui, ItemStack> {

    public GuiBuilder(final @NotNull GuiContainerType containerType) {
        super(PaperGuiSettings.get(), containerType);
    }

    @Override
    public Gui build() {
        if (!(getContainerType() instanceof final PaperContainerType paperContainerType)) {
            throw new IllegalArgumentException("Container type provided is not supported on Paper platform!");
        }

        return new Gui(
            getTitle(),
            getComponents(),
            paperContainerType,
            getComponentRenderer(),
            getClickHandler(),
            getSpamPreventionDuration()
        );
    }
}
