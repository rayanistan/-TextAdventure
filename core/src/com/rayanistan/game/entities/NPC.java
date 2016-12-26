package com.rayanistan.game.entities;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

import static com.rayanistan.game.utils.WorldUtils.Constants.PPM;

public abstract class NPC {
    // NPC entities need sprites for a visual representation in order to render to sprite batch
    protected Sprite sprite;

    // NPC entities need box2d bodies to handle box2d intersection
    protected Body body;

    // Need animation timer to keep track of which animation the npc is on
    protected float animationTimer;

    // Need currentAnimation to store animation
    protected Animation currentAnimation;

    protected NPC(Array<TextureAtlas.AtlasRegion> frames, float timer) {
        sprite = new Sprite();

        animationTimer = 0f;

        this.currentAnimation = new Animation(timer, frames);
    }

    public Vector2 getCenter() {
        return new Vector2(body.getPosition().x * PPM, body.getPosition().y * PPM);
    }

    public void render(SpriteBatch batch) {
        // Draw sprite to the screen
        sprite.draw(batch);
    }

    public abstract void update(float dt);
}
