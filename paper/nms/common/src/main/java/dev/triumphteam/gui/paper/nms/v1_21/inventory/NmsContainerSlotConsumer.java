package dev.triumphteam.gui.paper.nms.v1_21.inventory;

@FunctionalInterface
public interface NmsContainerSlotConsumer {

    void accept(final int slot, final int x, final int y);
}
