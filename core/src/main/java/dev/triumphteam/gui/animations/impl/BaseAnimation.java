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
    protected final List<Frame> frames;

    public BaseAnimation(int delay, List<Frame> frames) {
        this.delay = delay;
        this.frames = frames;

        this.reset();
    }

    public void to(BaseGui gui) {
        Validate.notNull(gui, "Gui cannot be null");
        this.gui = gui;
    }

    @Override
    public void reset() {
        current = 0;
    }

    @Override
    public @Nullable Frame nextFrame() {
        Validate.notNull(gui, "Gui has not been linked to this animation. Please link it by calling the to(BaseGui) method");
        current++;

        if (frames.size() == current) {
            return this.onFinish();
        }

        return frames.get(current).apply(gui);
    }

    @Override
    public void start() {
        Validate.notNull(gui, "Gui has not been linked to this animation. Please link it by calling the to(BaseGui) method");

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
    public AnimationRunner getRunnable() {
        return runner;
    }

    /**
     * @return The frame to return when the animation is finished
     */
    public abstract Frame onFinish();
}
