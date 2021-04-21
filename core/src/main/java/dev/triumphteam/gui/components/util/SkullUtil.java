package dev.triumphteam.gui.components.util;

import org.bukkit.Material;

public final class SkullUtil {

    /**
     * The main SKULL material for the version
     */
    public static final Material SKULL = getSkullMaterial();

    /**
     * Gets the correct {@link Material} for the version
     *
     * @return The correct SKULL {@link Material}
     */
    private static Material getSkullMaterial() {
        if (VersionHelper.isItemLegacy()) {
            return Material.valueOf("SKULL_ITEM");
        }

        return Material.valueOf("PLAYER_HEAD");
    }

}
