package com.rayanistan.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class FocalPointComponent implements Component, Pool.Poolable {

    public Vector2 position = new Vector2();
    public boolean current = false;

    @Override
    public void reset() {
        position = new Vector2();
        current = false;
    }
}
