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

package dev.triumphteam.gui.animations.impl;

import dev.triumphteam.gui.animations.Animation;
import dev.triumphteam.gui.animations.AnimationRunner;
import dev.triumphteam.gui.animations.Frame;
import dev.triumphteam.gui.guis.BaseGui;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BaseAnimation implements Animation {
    protected int current;
    protected BaseGui gui;
    protected AnimationRunner runner;
    protected final int delay;
    protected List<Frame> frames;

    public BaseAnimation(int delay, List<Frame> frames) {
        this.delay = delay;
        this.frames = frames;

        this.reset();
    }

    @Override
    public void reset() {
        current = 0;
    }

    @Override
    public @Nullable Frame nextFrame() {
        Validate.notNull(gui, "Gui has not been linked to this animation.");
        current++;

        if (frames.size() == current) {
            return this.onFinish();
        }

        return frames.get(current).apply(gui);
    }

    @Override
    public void start(BaseGui gui) {
        Validate.notNull(gui, "Gui cannot be null.");
        this.gui = gui;

        this.reset();
        frames.get(current).apply(gui);

        if (runner != null) {
            runner.cancel();
            runner = null;
        }

        this.runner = new AnimationRunner(this);
    }

    @Override
    public int getDelay() {
        return delay;
    }

    @Override
    public BaseGui getGui() {
        return gui;
    }

    @Override
    public AnimationRunner getRunnable() {
        return runner;
    }

    @Override
    public void stop() {
        Validate.notNull(gui, "Gui has not been linked to this animation. Please link it by calling the to(BaseGui) method");

        if (this.runner != null && !this.runner.isCancelled()) {
            this.runner.cancel();
        }
        frames.get(0).fallback(gui);
    }

    /**
     * @return The frame to return when the animation is finished
     */
    public abstract Frame onFinish();
}
