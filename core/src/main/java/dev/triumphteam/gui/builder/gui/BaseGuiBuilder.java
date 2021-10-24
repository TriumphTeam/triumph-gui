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
package dev.triumphteam.gui.builder.gui;

import dev.triumphteam.gui.components.InteractionModifier;
import dev.triumphteam.gui.components.exception.GuiException;
import dev.triumphteam.gui.guis.BaseGui;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * The base for all the GUI builders this is due to some limitations
 * where some builders will have unique features based on the GUI type
 *
 * @param <G> The Type of {@link BaseGui}
 */
@SuppressWarnings("unchecked")
public abstract class BaseGuiBuilder<G extends BaseGui, B extends BaseGuiBuilder<G, B>> {

    private Component title = null;
    private int rows = 1;
    private final EnumSet<InteractionModifier> interactionModifiers = EnumSet.noneOf(InteractionModifier.class);

    private Consumer<G> consumer;

    /**
     * Sets the rows for the GUI
     * This will only work on CHEST {@link dev.triumphteam.gui.components.GuiType}
     *
     * @param rows The amount of rows
     * @return The builder
     */
    @NotNull
    @Contract("_ -> this")
    public B rows(final int rows) {
        this.rows = rows;
        return (B) this;
    }

    /**
     * Sets the title for the GUI
     * This will be either a Component or a String
     *
     * @param title The GUI title
     * @return The builder
     */
    @NotNull
    @Contract("_ -> this")
    public B title(@NotNull final Component title) {
        this.title = title;
        return (B) this;
    }

    /**
     * Disable item placement inside the GUI
     *
     * @return The builder
     * @since 3.0.0
     * @author SecretX
     */
    @NotNull
    @Contract(" -> this")
    public B disableItemPlace() {
        interactionModifiers.add(InteractionModifier.PREVENT_ITEM_PLACE);
        return (B) this;
    }

    /**
     * Disable item retrieval inside the GUI
     *
     * @return The builder
     * @since 3.0.0
     * @author SecretX
     */
    @NotNull
    @Contract(" -> this")
    public B disableItemTake() {
        interactionModifiers.add(InteractionModifier.PREVENT_ITEM_TAKE);
        return (B) this;
    }

    /**
     * Disable item swap inside the GUI
     *
     * @return The builder
     * @since 3.0.0
     * @author SecretX
     */
    @NotNull
    @Contract(" -> this")
    public B disableItemSwap() {
        interactionModifiers.add(InteractionModifier.PREVENT_ITEM_SWAP);
        return (B) this;
    }

    /**
     * Disable item drop inside the GUI
     *
     * @return The builder
     * @since 3.0.3
     */
    @NotNull
    @Contract(" -> this")
    public B disableItemDrop() {
        interactionModifiers.add(InteractionModifier.PREVENT_ITEM_DROP);
        return (B) this;
    }

    /**
     * Disable other GUI actions
     * This option pretty much disables creating a clone stack of the item
     *
     * @return The builder
     * @since 3.0.4
     */
    @NotNull
    @Contract(" -> this")
    public B disableOtherActions() {
        interactionModifiers.add(InteractionModifier.PREVENT_OTHER_ACTIONS);
        return (B) this;
    }

    /**
     * Disable all the modifications of the GUI, making it immutable by player interaction
     *
     * @return The builder
     * @since 3.0.0
     * @author SecretX
     */
    @NotNull
    @Contract(" -> this")
    public B disableAllInteractions() {
        interactionModifiers.addAll(InteractionModifier.VALUES);
        return (B) this;
    }

    /**
     * Allows item placement inside the GUI
     *
     * @return The builder
     * @since 3.0.0
     * @author SecretX
     */
    @NotNull
    @Contract(" -> this")
    public B enableItemPlace() {
        interactionModifiers.remove(InteractionModifier.PREVENT_ITEM_PLACE);
        return (B) this;
    }

    /**
     * Allow items to be taken from the GUI
     *
     * @return The builder
     * @since 3.0.0
     * @author SecretX
     */
    @NotNull
    @Contract(" -> this")
    public B enableItemTake() {
        interactionModifiers.remove(InteractionModifier.PREVENT_ITEM_TAKE);
        return (B) this;
    }

    /**
     * Allows item swap inside the GUI
     *
     * @return The builder
     * @since 3.0.0
     * @author SecretX
     */
    @NotNull
    @Contract(" -> this")
    public B enableItemSwap() {
        interactionModifiers.remove(InteractionModifier.PREVENT_ITEM_SWAP);
        return (B) this;
    }

    /**
     * Allows item drop inside the GUI
     *
     * @return The builder
     * @since 3.0.3
     */
    @NotNull
    @Contract(" -> this")
    public B enableItemDrop() {
        interactionModifiers.remove(InteractionModifier.PREVENT_ITEM_DROP);
        return (B) this;
    }

    /**
     * Enable other GUI actions
     * This option pretty much enables creating a clone stack of the item
     *
     * @return The builder
     * @since 3.0.4
     */
    @NotNull
    @Contract(" -> this")
    public B enableOtherActions() {
        interactionModifiers.remove(InteractionModifier.PREVENT_OTHER_ACTIONS);
        return (B) this;
    }

    /**
     * Enable all modifications of the GUI, making it completely mutable by player interaction
     *
     * @return The builder
     * @since 3.0.0
     * @author SecretX
     */
    @NotNull
    @Contract(" -> this")
    public B enableAllInteractions() {
        interactionModifiers.clear();
        return (B) this;
    }

    /**
     * Applies anything to the GUI once it's created
     * Can be pretty useful for setting up small things like default actions
     *
     * @param consumer A {@link Consumer} that passes the built GUI
     * @return The builder
     */
    @NotNull
    @Contract("_ -> this")
    public B apply(@NotNull final Consumer<G> consumer) {
        this.consumer = consumer;
        return (B) this;
    }

    /**
     * Creates the given GuiBase
     * Has to be abstract because each GUI are different
     *
     * @return The new {@link BaseGui}
     */
    @NotNull
    @Contract(" -> new")
    public abstract G create();

    /**
     * Getter for the title
     *
     * @return The current title
     */
    @NotNull
    protected Component getTitle() {
        if (title == null) {
            throw new GuiException("GUI title is missing!");
        }

        return title;
    }

    /**
     * Getter for the rows
     *
     * @return The amount of rows
     */
    protected int getRows() {
        return rows;
    }

    /**
     * Getter for the consumer
     *
     * @return The consumer
     */
    @Nullable
    protected Consumer<G> getConsumer() {
        return consumer;
    }


    /**
     * Getter for the set of interaction modifiers
     * @return The set of {@link InteractionModifier}
     * @since 3.0.0
     * @author SecretX
     */
    @NotNull
    protected Set<InteractionModifier> getModifiers() {
        return interactionModifiers;
    }
}
