package dev.triumphteam.gui.nbt;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class PdcContext implements NbtContext {

    private static final Plugin plugin = JavaPlugin.getProvidingPlugin(PdcContext.class);

    private final ItemMeta itemMeta;

    public PdcContext(final ItemMeta itemMeta) {
        this.itemMeta = itemMeta;
    }

    @Override
    public void set(final @NotNull String key, final @NotNull String value) {
        itemMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, key), PersistentDataType.STRING, value);
    }

    @Override
    public String get(final @NotNull String key) {
        return itemMeta.getPersistentDataContainer().get(new NamespacedKey(plugin, key), PersistentDataType.STRING);
    }

}
