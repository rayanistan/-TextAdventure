package com.rayanistan.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Pool;

public class SpriteComponent implements Component, Pool.Poolable {
    public Sprite sprite = new Sprite();

    @Override
    public void reset() {
        sprite.getTexture().dispose();
        sprite = new Sprite();
    }
}
