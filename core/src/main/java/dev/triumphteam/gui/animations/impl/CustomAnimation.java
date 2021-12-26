package dev.triumphteam.gui.animations.impl;

import dev.triumphteam.gui.animations.Frame;

import java.util.List;

public class CustomAnimation extends BaseAnimation {

    public CustomAnimation(int delay, List<Frame> frames) {
        super(delay, frames);
    }

    @Override
    public Frame onFinish() {
        return null;
    }

}
