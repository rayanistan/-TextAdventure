package com.rayanistan.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class PlayerComponent implements Component, Pool.Poolable {

    @Override
    public void reset() {
        numOfContactsWithFoot = 0;
    }

    public enum State {
        WALKING, JUMPING, IDLING
    }

    public int numOfContactsWithFoot = 0;
}
