package dev.triumphteam.gui.paper.nms;

import com.google.common.primitives.Ints;
import dev.triumphteam.gui.exception.TriumphGuiException;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class VersionHelper {

    private static final String CRAFTBUKKIT_PACKAGE = Bukkit.getServer().getClass().getPackage().getName();

    /**
     * The current server version.
     */
    private static final int CURRENT_VERSION = getCurrentVersion();


    /**
     * Gets the current server version
     *
     * @return A protocol like number representing the version, for example, 1.16.5 -> 1165
     */
    private static int getCurrentVersion() {
        // No need to cache since will only run once
        final Matcher matcher = Pattern.compile("(?<version>\\d+\\.\\d+)(?<patch>\\.\\d+)?").matcher(Bukkit.getBukkitVersion());

        final StringBuilder stringBuilder = new StringBuilder();
        if (matcher.find()) {
            stringBuilder.append(matcher.group("version").replace(".", ""));
            final String patch = matcher.group("patch");
            if (patch == null) stringBuilder.append("0");
            else stringBuilder.append(patch.replace(".", ""));
        }

        final Integer version = Ints.tryParse(stringBuilder.toString());

        // Should never fail
        if (version == null) throw new TriumphGuiException("Could not retrieve server version!");

        return version;
    }
}
