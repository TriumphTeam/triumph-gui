package dev.triumphteam.gui.animations.impl;

import dev.triumphteam.gui.animations.Frame;

import java.util.List;

public class InfiniteAnimation extends BaseAnimation {

    public InfiniteAnimation(int delay, List<Frame> frames) {
        super(delay, frames);
    }

    @Override
    public Frame onFinish() {
        this.reset();
        return this.frames.get(current);
    }
}
