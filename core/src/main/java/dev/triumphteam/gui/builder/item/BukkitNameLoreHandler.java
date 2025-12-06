/**
 * MIT License
 *
 * Copyright (c) 2021 TriumphTeam
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
package dev.triumphteam.gui.builder.item;

import dev.triumphteam.gui.components.exception.GuiException;
import dev.triumphteam.gui.components.util.Legacy;
import dev.triumphteam.gui.components.util.VersionHelper;
import net.kyori.adventure.platform.bukkit.MinecraftComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public final class BukkitNameLoreHandler implements NameLoreHandler {

    private static final Field DISPLAY_NAME_FIELD;
    private static final Field LORE_FIELD;

    private static final BukkitNameLoreHandler INSTANCE = new BukkitNameLoreHandler();

    static {
        try {
            final Class<?> metaClass = VersionHelper.craftClass("inventory.CraftMetaItem");

            DISPLAY_NAME_FIELD = metaClass.getDeclaredField("displayName");
            DISPLAY_NAME_FIELD.setAccessible(true);

            LORE_FIELD = metaClass.getDeclaredField("lore");
            LORE_FIELD.setAccessible(true);
        } catch (NoSuchFieldException | ClassNotFoundException exception) {
            throw new GuiException("Could not retrieve displayName nor lore field for ItemBuilder.", exception);
        }
    }

    public static @NotNull BukkitNameLoreHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void name(final @NotNull ItemMeta itemMeta, final @NotNull Component name) {
        if (VersionHelper.IS_COMPONENT_LEGACY) {
            itemMeta.setDisplayName(Legacy.SERIALIZER.serialize(name));
            return;
        }

        try {
            DISPLAY_NAME_FIELD.set(itemMeta, this.serializeComponent(name));
        } catch (IllegalAccessException exception) {
            throw new GuiException("Could not set display name for ItemBuilder.", exception);
        }
    }

    @Override
    public void lore(final @NotNull ItemMeta itemMeta, final @NotNull List<Component> lore) {
        if (VersionHelper.IS_COMPONENT_LEGACY) {
            itemMeta.setLore(lore.stream().filter(Objects::nonNull).map(Legacy.SERIALIZER::serialize).collect(Collectors.toList()));
            return;
        }

        final List<Object> jsonLore = lore.stream()
                .filter(Objects::nonNull)
                .map(this::serializeComponent)
                .collect(Collectors.toList());

        try {
            LORE_FIELD.set(itemMeta, jsonLore);
        } catch (IllegalAccessException exception) {
            throw new GuiException("Could not set lore for ItemBuilder.", exception);
        }
    }

    @Override
    public void lore(final @NotNull ItemMeta itemMeta, final @NotNull Consumer<List<@Nullable Component>> lore) {
        List<Component> components;
        if (VersionHelper.IS_COMPONENT_LEGACY) {
            final List<String> stringLore = itemMeta.getLore();
            components = (stringLore == null) ? new ArrayList<>() : stringLore.stream().map(Legacy.SERIALIZER::deserialize).collect(Collectors.toList());
        } else {
            try {
                final List<Object> jsonLore = (List<Object>) LORE_FIELD.get(itemMeta);
                // The field is null by default ._.
                components = (jsonLore == null) ? new ArrayList<>() : jsonLore.stream().map(this::deserializeComponent).collect(Collectors.toList());
            } catch (IllegalAccessException exception) {
                throw new GuiException("Could not get lore for ItemBuilder.", exception);
            }
        }

        lore.accept(components);
        lore(itemMeta, components);
    }

    /**
     * Serializes the component with the right {@link net.kyori.adventure.text.serializer.ComponentSerializer} for the current MC version
     *
     * @param component component to serialize
     * @return the serialized representation of the component
     */
    private @NotNull Object serializeComponent(@NotNull final Component component) {
        if (VersionHelper.IS_ITEM_NAME_COMPONENT) {
            //noinspection UnstableApiUsage
            return MinecraftComponentSerializer.get().serialize(component);
        } else {
            return GsonComponentSerializer.gson().serialize(component);
        }
    }

    /**
     * Deserializes the object with the right {@link net.kyori.adventure.text.serializer.ComponentSerializer} for the current MC version
     *
     * @param obj object to deserialize
     * @return the component
     */
    private @NotNull Component deserializeComponent(@NotNull final Object obj) {
        if (VersionHelper.IS_ITEM_NAME_COMPONENT) {
            //noinspection UnstableApiUsage
            return MinecraftComponentSerializer.get().deserialize(obj);
        } else {
            return GsonComponentSerializer.gson().deserialize((String) obj);
        }
    }
}
