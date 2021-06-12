package dev.triumphteam.gui.builder.gui;

import dev.triumphteam.gui.guis.StorageGui;
import org.jetbrains.annotations.Contract;

import java.util.function.Consumer;

/**
 * The simple GUI builder is used for creating a {@link StorageGui}
 */
public final class StorageBuilder extends BaseGuiBuilder<StorageGui, StorageBuilder> {

    /**
     * Creates a new {@link StorageGui}
     *
     * @return A new {@link StorageGui}
     */
    @Contract(" -> new")
    @Override
    public StorageGui create() {
        final StorageGui gui = new StorageGui(getRows(), getTitle(), getModifiers());

        final Consumer<StorageGui> consumer = getConsumer();
        if (consumer != null) consumer.accept(gui);

        return gui;
    }

}
