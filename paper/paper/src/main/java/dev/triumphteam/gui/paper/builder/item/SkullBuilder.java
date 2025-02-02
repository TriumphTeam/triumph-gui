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
package dev.triumphteam.gui.paper.builder.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.triumphteam.gui.exception.TriumphGuiException;
import dev.triumphteam.gui.paper.util.SkullUtil;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public final class SkullBuilder extends AbstractItemBuilder<SkullBuilder> {

    private static final Field PROFILE_FIELD;

    static {
        Field field;

        try {
            final SkullMeta skullMeta = (SkullMeta) SkullUtil.skull().getItemMeta();
            field = skullMeta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            field = null;
        }

        PROFILE_FIELD = field;
    }

    SkullBuilder() {
        super(SkullUtil.skull());
    }

    SkullBuilder(final @NotNull ItemStack itemStack) {
        super(itemStack);
        if (!SkullUtil.isPlayerSkull(itemStack)) {
            throw new TriumphGuiException("SkullBuilder requires the material to be a PLAYER_HEAD/SKULL_ITEM!");
        }
    }

    /**
     * Sets the skull texture using a BASE64 string
     *
     * @param texture The base64 texture
     * @param profileId The unique id of the profile
     * @return {@link SkullBuilder}
     */
    @NotNull
    @Contract("_, _ -> this")
    public SkullBuilder texture(@NotNull final String texture, @NotNull final UUID profileId) {
        if (!SkullUtil.isPlayerSkull(asItemStack())) return this;

        int version = Integer.parseInt(Bukkit.getServer().getMinecraftVersion().replace(".", ""));
        if (version >= 1201) {
            final String textureUrl = SkullUtil.getSkinUrl(texture);

            if (textureUrl == null) {
                return this;
            }

            final SkullMeta skullMeta = (SkullMeta) asItemStack().getItemMeta();
            final PlayerProfile profile = Bukkit.createPlayerProfile(profileId, "");
            final PlayerTextures textures = profile.getTextures();

            try {
                textures.setSkin(new URL(textureUrl));
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return this;
            }

            profile.setTextures(textures);
            skullMeta.setOwnerProfile(profile);
            meta(skullMeta);
            return this;
        }

        if (PROFILE_FIELD == null) {
            return this;
        }

        final SkullMeta skullMeta = (SkullMeta) meta();
        final GameProfile profile = new GameProfile(profileId, "");
        profile.getProperties().put("textures", new Property("textures", texture));

        try {
            PROFILE_FIELD.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }

        meta(skullMeta);
        return this;
    }

    /**
     * Sets the skull texture using a BASE64 string
     *
     * @param texture The base64 texture
     * @return {@link SkullBuilder}
     */
    @NotNull
    @Contract("_ -> this")
    public SkullBuilder texture(@NotNull final String texture) {
        return texture(texture, UUID.randomUUID());
    }

    /**
     * Sets skull owner via bukkit methods
     *
     * @param player {@link OfflinePlayer} to set skull of
     * @return {@link SkullBuilder}
     */
    @NotNull
    @Contract("_ -> this")
    public SkullBuilder owner(@NotNull final OfflinePlayer player) {
        if (!SkullUtil.isPlayerSkull(asItemStack())) return this;

        final SkullMeta skullMeta = (SkullMeta) meta();

        skullMeta.setOwningPlayer(player);

        meta(skullMeta);
        return this;
    }

}
