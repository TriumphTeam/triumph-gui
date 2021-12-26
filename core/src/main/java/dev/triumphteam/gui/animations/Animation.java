/*
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

package dev.triumphteam.gui.animations;

import dev.triumphteam.gui.animations.impl.SimpleAnimation;
import dev.triumphteam.gui.animations.impl.InfiniteAnimation;
import dev.triumphteam.gui.guis.BaseGui;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public interface Animation {

    /**
     * Reset to the initial frame
     */
    void reset();

    /**
     * Go to the next frame
     * @return the next frame or null if it is the last frame
     */
    @Nullable Frame nextFrame();

    /**
     * Start the animation
     * @param gui The gui to apply the animation to
     */
    void start(BaseGui gui);

    /**
     * Stops the animation
     */
    void stop();

    /**
     * @return the delay between frames
     */
    int getDelay();

    /**
     * @return the bukkit runnable that handles frame updates
     */
    AnimationRunner getRunnable();

    /**
     * @return the gui that this animation is applyed to
     */
    BaseGui getGui();

    /**
     * Create a simple animation that stop at the end of the frames
     * @param delay the delay between frames
     * @param frames the frames
     * @return the animation
     */
    static @NotNull Animation of(int delay, Frame... frames) {
        return new SimpleAnimation(delay, Arrays.asList(frames));
    }

    /**
     * Create a loop animation
     * @param delay the delay between frames
     * @param frames the frames
     * @return the animation
     */
    static @NotNull Animation infinite(int delay, Frame... frames) {
        return new InfiniteAnimation(delay, Arrays.asList(frames));
    }
}
