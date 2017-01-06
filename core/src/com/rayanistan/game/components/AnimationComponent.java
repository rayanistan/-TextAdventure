package com.rayanistan.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Pool;

public class AnimationComponent<T> implements Component, Pool.Poolable {
    public ArrayMap<T, Animation> animations = new ArrayMap<>();

    public float timer = 0.0f;

    public T state;

    public TextureRegion getFrame() {
        return animations.get(state).getKeyFrame(timer);
    }

    public void setAnimation(T state) {
        this.state = state;
        timer = 0.0f;
    }

    @Override
    public void reset() {
        animations = new ArrayMap<>();
        timer = 0.0f;
    }
}
