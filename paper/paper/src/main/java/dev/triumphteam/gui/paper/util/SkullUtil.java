package dev.triumphteam.gui.paper.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Base64;

public final class SkullUtil {

    /**
     * The main SKULL material for the version
     */
    private static final Material SKULL = getSkullMaterial();
    private static final Gson GSON = new Gson();

    /**
     * Gets the correct {@link Material} for the version
     *
     * @return The correct SKULL {@link Material}
     */
    private static Material getSkullMaterial() {
        return Material.PLAYER_HEAD;
    }

    /**
     * Create a player skull
     *
     * @return player skull
     */
    @SuppressWarnings("deprecation")
    public static ItemStack skull() {
        return new ItemStack(SKULL);
    }

    /**
     * Checks if an {@link ItemStack} is a player skull
     *
     * @param item item to check
     * @return whether the item is a player skull
     */
    @SuppressWarnings("deprecation")
    public static boolean isPlayerSkull(@NotNull final ItemStack item) {
        return item.getType() == SKULL;
    }

    /**
     * Decode a base64 string and extract the url of the skin. Example:
     * <br>
     * - Base64: {@code eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGNlYjE3MDhkNTQwNGVmMzI2MTAzZTdiNjA1NTljOTE3OGYzZGNlNzI5MDA3YWM5YTBiNDk4YmRlYmU0NjEwNyJ9fX0=}
     * <br>
     * - JSON: {@code {"textures":{"SKIN":{"url":"http://textures.minecraft.net/texture/dceb1708d5404ef326103e7b60559c9178f3dce729007ac9a0b498bdebe46107"}}}}
     * <br>
     * - Result: {@code http://textures.minecraft.net/texture/dceb1708d5404ef326103e7b60559c9178f3dce729007ac9a0b498bdebe46107}
     * @param base64Texture the texture
     * @return the url of the texture if found, otherwise {@code null}
     */
    public static String getSkinUrl(String base64Texture) {
        final String decoded = new String(Base64.getDecoder().decode(base64Texture));
        final JsonObject object = GSON.fromJson(decoded, JsonObject.class);

        final JsonElement textures = object.get("textures");

        if (textures == null) {
            return null;
        }

        final JsonElement skin = textures.getAsJsonObject().get("SKIN");

        if (skin == null) {
            return null;
        }

        final JsonElement url = skin.getAsJsonObject().get("url");
        return url == null ? null : url.getAsString();
    }

}