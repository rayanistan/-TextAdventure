package com.rayanistan.game.components;

import com.badlogic.ashley.core.Component;

public class StateComponent implements Component{
    private int state;
    public float timer = 0.0f;
    public boolean isLooping = true;

    public int get() {
        return state;
    }

    public void set(int nextState) {
        state = nextState;
        timer = 0.0f;
    }
}
