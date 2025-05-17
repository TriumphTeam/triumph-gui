package dev.triumphteam.gui.paper.nms;

import dev.triumphteam.gui.exception.TriumphGuiException;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

public final class NmsGuiReflectionHandler {

    private final Class<?> simpleContainerClass;
    private final Field bukkitOwnerField;

    public NmsGuiReflectionHandler(final @NotNull Class<?> simpleContainerClass) {
        this.simpleContainerClass = simpleContainerClass;
        try {
            this.bukkitOwnerField = simpleContainerClass.getDeclaredField("bukkitOwner");
            this.bukkitOwnerField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Could not find bukkitOwner field.", e);
        }
    }

    public void setBukkitOwner(final @NotNull Object container, final @NotNull InventoryHolder bukkitOwner) {
        try {
            bukkitOwnerField.set(container, bukkitOwner);
        } catch (IllegalAccessException e) {
            throw new TriumphGuiException("Could not set bukkitOwner field.", e);
        }
    }
}
