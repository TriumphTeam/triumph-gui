/**
 * MIT License
 * <p>
 * Copyright (c) 2021 TriumphTeam
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.gui.builder.item;

/**
 * Used to hide item attributes
 */
public enum ItemFlag {

    /**
     * Used to show/hide enchants
     */
    HIDE_ENCHANTS,
    /**
     * Used to show/hide attributes
     */
    HIDE_ATTRIBUTES,
    /**
     * Used to show/hide the unbreakable state
     */
    HIDE_UNBREAKABLE,
    /**
     * Used to show/hide what the ItemStack can break or destroy
     */
    HIDE_DESTROYS,
    /**
     * Used to show/hide where this ItemStack can be built or placed on
     */
    HIDE_PLACED_ON,
    /**
     * Used to show/hide potion effects on this ItemStack
     */
    HIDE_POTION_EFFECTS
}