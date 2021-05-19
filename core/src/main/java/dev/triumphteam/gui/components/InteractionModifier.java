package dev.triumphteam.gui.components;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Used to control what kind of interaction can happen inside a GUI
 *
 * @since 3.0.0
 * @author SecretX
 */
public enum InteractionModifier {
    PREVENT_ITEM_PLACE,
    PREVENT_ITEM_TAKE,
    PREVENT_ITEM_SWAP;

    public static final Set<InteractionModifier> VALUES = Collections.unmodifiableSet(EnumSet.allOf(InteractionModifier.class));
}
