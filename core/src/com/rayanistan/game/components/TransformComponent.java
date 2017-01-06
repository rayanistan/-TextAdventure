package com.rayanistan.game.components;

import com.badlogic.ashley.core.Component;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class TransformComponent implements Component, Pool.Poolable {

    public Vector2 position = new Vector2();

    public Vector2 velocity = new Vector2();

    public Vector2 scale = new Vector2(1f, 1f);

    public float rotation = 0;

    public Orientation orientation = Orientation.RIGHT;

    @Override
    public void reset() {
        position = new Vector2();
        velocity = new Vector2();
        scale = new Vector2();
        rotation = 0;
    }

    public enum Orientation {
        RIGHT (false),
        LEFT (true);

        public final boolean value;

        Orientation(boolean value) {
            this.value = value;
        }
    }
}
