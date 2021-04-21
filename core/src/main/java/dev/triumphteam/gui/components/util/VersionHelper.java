package dev.triumphteam.gui.components.util;

import com.google.common.primitives.Ints;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class for detecting server version for legacy support :(
 */
public final class VersionHelper {

    // Unbreakable change
    private static final int V1_12_1 = 1121;
    // Material change
    private static final int V1_13_1 = 1131;
    // Paper changes
    private static final int V1_16_5 = 1165;

    private static final int CURRENT_VERSION = getCurrentVersion();

    private static final boolean IS_PAPER = checkPaper();

    /**
     * Checks if the version supports Components or not
     * Paper versions above 1.16.5 would be true
     * Spigot always false
     *
     * @return Whether or not the version supports components
     */
    public static boolean isComponentLegacy() {
        return !IS_PAPER || CURRENT_VERSION < V1_16_5;
    }

    /**
     * Checks if the version is lower than 1.13 due to the item changes
     *
     * @return Whether or not the version is lower than 1.13
     */
    public static boolean isItemLegacy() {
        return CURRENT_VERSION < V1_13_1;
    }

    /**
     * Check if the server has access to the Paper API
     * Taken from <a href="https://github.com/PaperMC/PaperLib">PaperLib</a>
     *
     * @return True if on Paper server (or forks), false anything else
     */
    private static boolean checkPaper() {
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            return true;
        } catch (ClassNotFoundException ignored) {
            return false;
        }
    }

    /**
     * Gets the current server version
     *
     * @return A protocol like number representing the version, for example 1.16.5 - 1165
     */
    public static int getCurrentVersion() {
        final Matcher matcher = Pattern.compile("(?<version>1\\.\\d+\\.\\d+)").matcher(Bukkit.getBukkitVersion());
        if (!matcher.find()) return 0;

        final Integer version = Ints.tryParse(matcher.group("version").replace(".", ""));
        if (version == null) return 0;
        return version;
    }

}
