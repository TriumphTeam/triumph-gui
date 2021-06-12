package dev.triumphteam.gui.components.util;

import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class Legacy {

    /**
     * Since old versions and fucking spigot don't use the components, I use the serializer
     * with the stupid format to guarantee compatibility
     */
    public static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.builder()
            .hexColors()
            .useUnusualXRepeatedCharacterHexFormat()
            .build();

    private Legacy() {
        throw new UnsupportedOperationException("Class should not be instantiated!");
    }

}
