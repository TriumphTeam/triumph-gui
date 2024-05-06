package dev.triumphteam.gui.layout;

import dev.triumphteam.gui.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface GuiLayout {

    @NotNull
    List<@NotNull Slot> generatePositions();
}
