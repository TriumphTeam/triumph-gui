package dev.triumphteam.gui.paper.nms;

@FunctionalInterface
public interface NmsContainerSlotConsumer {

    void accept(final int slot, final int x, final int y);
}
