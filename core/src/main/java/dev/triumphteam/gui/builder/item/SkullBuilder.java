package dev.triumphteam.gui.builder.item;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.triumphteam.gui.components.exception.GuiException;
import dev.triumphteam.gui.components.util.SkullUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * New builder for skull only, created to separate the specific features for skulls
 * Soon I'll add more useful features to this builder
 */
public final class SkullBuilder extends BaseItemBuilder<SkullBuilder> {

    SkullBuilder() {
        super(new ItemStack(SkullUtil.SKULL));
    }

    SkullBuilder(final @NotNull ItemStack itemStack) {
        super(itemStack);
        if (itemStack.getType() != SkullUtil.SKULL) {
            throw new GuiException("SkullBuilder requires the material to be a PLAYER_HEAD/SKULL_ITEM!");
        }
    }

    /**
     * Sets the skull texture using a BASE64 string
     *
     * @param texture The base64 texture
     * @return {@link SkullBuilder}
     */
    public SkullBuilder texture(@NotNull final String texture) {
        if (getItemStack().getType() != SkullUtil.SKULL) return this;

        SkullMeta skullMeta = (SkullMeta) getMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));
        Field profileField;

        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ex) {
            ex.printStackTrace();
        }

        setMeta(skullMeta);
        return this;
    }

    /**
     * Sets skull owner via bukkit methods
     *
     * @param player {@link OfflinePlayer} to set skull of
     * @return {@link SkullBuilder}
     */
    public SkullBuilder owner(@NotNull final OfflinePlayer player) {
        if (getItemStack().getType() != SkullUtil.SKULL) return this;

        final SkullMeta skullMeta = (SkullMeta) getMeta();
        skullMeta.setOwningPlayer(player);

        setMeta(skullMeta);
        return this;
    }

}
