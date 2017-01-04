package com.rayanistan.game.components;

import com.badlogic.ashley.core.Component;

import com.badlogic.gdx.math.Vector2;

public class TransformComponent implements Component {

    public Vector2 position = new Vector2();

    public Vector2 velocity = new Vector2();

    public Vector2 scale = new Vector2();

    public float rotation = 0;

    public Orientation orientation = Orientation.RIGHT;

    public enum Orientation {
        RIGHT (false),
        LEFT (true);

        public final boolean spriteFlip;

        Orientation(boolean spriteFlip) {
            this.spriteFlip = spriteFlip;
        }
    }
}
