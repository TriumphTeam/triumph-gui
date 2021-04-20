package dev.triumphteam.gui.components.util;

import org.bukkit.Material;

public final class SkullUtil {

    public static final Material SKULL = getSkullMaterial();

    private static Material getSkullMaterial() {
        if (VersionHelper.isItemLegacy()) {
            return Material.valueOf("SKULL_ITEM");
        }
   
        return Material.valueOf("PLAYER_HEAD");
    }

}
