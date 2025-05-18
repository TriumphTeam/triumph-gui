/**
 * MIT License
 *
 * Copyright (c) 2024 TriumphTeam
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.gui.paper;

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
    public static final int CURRENT_VERSION = getCurrentVersion();

    public static final int V1_21_5 = 1215;

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
