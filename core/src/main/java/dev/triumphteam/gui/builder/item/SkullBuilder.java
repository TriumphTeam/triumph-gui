package dev.triumphteam.gui.builder.item;

import dev.triumphteam.gui.components.exception.GuiException;
import dev.triumphteam.gui.components.util.SkullUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

/**
 * New builder for skull only, created to separate the specific features for skulls
 * Soon I'll add more useful features to this builder
 */
public final class SkullBuilder extends ItemBuilder {

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
        return (SkullBuilder) setSkullTexture(texture);
    }

    /**
     * Sets skull owner via bukkit methods
     *
     * @param player {@link OfflinePlayer} to set skull of
     * @return {@link SkullBuilder}
     */
    public SkullBuilder owner(@NotNull final OfflinePlayer player) {
        return (SkullBuilder) setSkullOwner(player);
    }

    @Override
    public SkullBuilder name(final @NotNull Component name) {
        return (SkullBuilder) super.name(name);
    }

    @Override
    public SkullBuilder amount(final int amount) {
        return (SkullBuilder) super.amount(amount);
    }

    @Override
    public SkullBuilder lore(final @NotNull Component... lore) {
        return (SkullBuilder) super.lore(lore);
    }

    @Override
    public SkullBuilder lore(final @NotNull List<Component> lore) {
        return (SkullBuilder) super.lore(lore);
    }

    @Override
    public SkullBuilder lore(final @NotNull Consumer<List<Component>> lore) {
        return (SkullBuilder) super.lore(lore);
    }

    @Override
    public SkullBuilder enchant(final @NotNull Enchantment enchantment, final int level, final boolean ignoreLevelRestriction) {
        return (SkullBuilder) super.enchant(enchantment, level, ignoreLevelRestriction);
    }

    @Override
    public SkullBuilder enchant(final @NotNull Enchantment enchantment, final int level) {
        return (SkullBuilder) super.enchant(enchantment, level);
    }

    @Override
    public SkullBuilder enchant(final @NotNull Enchantment enchantment) {
        return (SkullBuilder) super.enchant(enchantment);
    }

    @Override
    public SkullBuilder disenchant(final @NotNull Enchantment enchantment) {
        return (SkullBuilder) super.disenchant(enchantment);
    }

    @Override
    public SkullBuilder flags(final @NotNull ItemFlag... flags) {
        return (SkullBuilder) super.flags(flags);
    }

    @Override
    public SkullBuilder unbreakable() {
        return (SkullBuilder) super.unbreakable();
    }

    @Override
    public SkullBuilder unbreakable(final boolean unbreakable) {
        return (SkullBuilder) super.unbreakable(unbreakable);
    }

    @Override
    public SkullBuilder glow() {
        return (SkullBuilder) super.glow();
    }

    @Override
    public SkullBuilder glow(final boolean glow) {
        return (SkullBuilder) super.glow(glow);
    }

    @Override
    public SkullBuilder pdc(final @NotNull Consumer<PersistentDataContainer> consumer) {
        return (SkullBuilder) super.pdc(consumer);
    }

    @Override
    public SkullBuilder model(final int modelData) {
        return (SkullBuilder) super.model(modelData);
    }

    @Override
    public SkullBuilder setNbt(final @NotNull String key, final @Nullable String value) {
        return (SkullBuilder) super.setNbt(key, value);
    }

    @Override
    public SkullBuilder removeNbt(final @NotNull String key) {
        return (SkullBuilder) super.removeNbt(key);
    }
}
