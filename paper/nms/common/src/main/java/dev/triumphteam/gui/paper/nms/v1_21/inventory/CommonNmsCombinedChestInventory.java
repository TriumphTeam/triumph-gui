package dev.triumphteam.gui.paper.nms.v1_21.inventory;

import org.jetbrains.annotations.NotNull;

import static dev.triumphteam.gui.container.type.GuiContainerType.COLUMNS;
import static dev.triumphteam.gui.container.type.GuiContainerType.PLAYER_INVENTORY_FULL_SIZE;
import static dev.triumphteam.gui.container.type.GuiContainerType.PLAYER_INVENTORY_HOTBAR_ROWS;

public final class CommonNmsCombinedChestInventory {

    // Honestly, the server never really uses these, but oh well.
    private static final int NMS_X_START = 8;
    private static final int NMS_Y_START = 18;
    private static final int NMS_SPACE = 18;

    private final int rows;

    public CommonNmsCombinedChestInventory(final int rows) {
        this.rows = rows;
    }

    public void generateSlots(final @NotNull NmsContainerSlotConsumer consumer) {
        final int combinedRows = rows + PLAYER_INVENTORY_HOTBAR_ROWS;
        for (int row = 0; row < combinedRows; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                consumer.accept(column + row * COLUMNS, NMS_X_START + column * NMS_SPACE, NMS_Y_START + row * NMS_SPACE);
            }
        }
    }

    public int fullContainerSize() {
        return topContainerSize() + PLAYER_INVENTORY_FULL_SIZE;
    }

    public int topContainerSize() {
        return rows * COLUMNS;
    }
}
