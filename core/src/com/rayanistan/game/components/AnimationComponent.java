package com.rayanistan.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ArrayMap;

import static com.rayanistan.game.components.AnimationComponent.State.IDLE;

public class AnimationComponent implements Component {
    public ArrayMap<State, Animation> animations = new ArrayMap<State, Animation>();

    public float timer = 0.0f;
    public boolean isLooping;
    public State state = IDLE;

    public enum State {
        // States for player
        IDLE,
        WALKING,
        JUMPING
    }

    public TextureRegion getFrame() {
        return animations.get(state).getKeyFrame(timer, isLooping);
    }

    public void setAnimation(State state, boolean isLooping) {
        this.state = state;
        this.isLooping = isLooping;
        timer = 0.0f;
    }
}
