package dev.triumphteam.gui.builder.item;

import dev.triumphteam.gui.components.exception.GuiException;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * Item builder for {@link Material#FIREWORK_ROCKET} & {@link Material#FIREWORK_ROCKET} only
 *
 * @author GabyTM <a href="https://github.com/iGabyTM">https://github.com/iGabyTM</a>
 * @since 3.0.1
 */
public class FireworkBuilder extends BaseItemBuilder<FireworkBuilder> {

    private static final Material STAR = Material.FIREWORK_STAR;
    private static final Material ROCKET = Material.FIREWORK_ROCKET;

    FireworkBuilder(@NotNull final ItemStack itemStack) {
        super(itemStack);
        if (itemStack.getType() != STAR && itemStack.getType() != ROCKET) {
            throw new GuiException("FireworkBuilder requires the material to be a FIREWORK_STAR/FIREWORK_ROCKET!");
        }
    }

    /**
     * Add several firework effects to this firework.
     *
     * @param effects effects to add
     * @return {@link FireworkBuilder}
     * @throws IllegalArgumentException If effects is null
     * @throws IllegalArgumentException If any effect is null (may be thrown after changes have occurred)
     * @since 3.0.1
     */
    public FireworkBuilder effect(@NotNull final FireworkEffect... effects) {
        return effect(Arrays.asList(effects));
    }

    /**
     * Add several firework effects to this firework.
     *
     * @param effects effects to add
     * @return {@link FireworkBuilder}
     * @throws IllegalArgumentException If effects is null
     * @throws IllegalArgumentException If any effect is null (may be thrown after changes have occurred)
     * @since 3.0.1
     */
    public FireworkBuilder effect(@NotNull final List<FireworkEffect> effects) {
        if (effects.isEmpty()) {
            return this;
        }

        if (getItemStack().getType() == STAR) {
            final FireworkEffectMeta effectMeta = (FireworkEffectMeta) getMeta();

            effectMeta.setEffect(effects.get(0));
            setMeta(effectMeta);
            return this;
        }

        final FireworkMeta fireworkMeta = (FireworkMeta) getMeta();

        fireworkMeta.addEffects(effects);
        setMeta(fireworkMeta);
        return this;
    }

    /**
     * Sets the approximate power of the firework. Each level of power is half
     * a second of flight time.
     *
     * @param power the power of the firework, from 0-128
     * @return {@link FireworkBuilder}
     * @throws IllegalArgumentException if {@literal height<0 or height>128}
     * @since 3.0.1
     */
    public FireworkBuilder power(final int power) {
        if (getItemStack().getType() == ROCKET) {
            final FireworkMeta fireworkMeta = (FireworkMeta) getMeta();

            fireworkMeta.setPower(power);
            setMeta(fireworkMeta);
        }

        return this;
    }

}
