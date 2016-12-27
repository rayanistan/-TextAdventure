package com.rayanistan.game.entities;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.rayanistan.game.utils.WorldUtils;

import static com.rayanistan.game.utils.WorldUtils.Constants.PPM;
import static com.rayanistan.game.utils.WorldUtils.Constants.WIZARD_BITS;

public final class Wizard extends NPC {

    public Wizard(World world, TextureAtlas atlas, Rectangle rect, short mask) {
        super(atlas.findRegions("w"), 0.75f);

        body = WorldUtils.createBox(world, rect.getX() + rect.getWidth() / 2
                , rect.getY() + rect.getHeight() / 2, rect.getWidth(), rect.getHeight(),
                false, sprite, WIZARD_BITS, mask);
    }

    @Override
    public void update(float dt) {
        // Update animation using animationTimer
        handleAnimation(dt);


        // Update size of box2d body
        PolygonShape shape = (PolygonShape) body.getFixtureList().first().getShape();
        shape.setAsBox(sprite.getWidth() / 2 / PPM * sprite.getScaleX(),
                sprite.getHeight() / 2 / PPM * sprite.getScaleX());

    }

    private void handleAnimation(float dt) {

        // Get currentFrame based off of animationTimer
        sprite.setRegion(currentAnimation.getKeyFrame(animationTimer, true));

        // Update scale of sprite (probably futile)
        sprite.setScale(1.75f, 1.75f);

        // Update position of sprite based off of box2d body
        sprite.setPosition(body.getPosition().x * PPM,
                body.getPosition().y * PPM - sprite.getHeight() / 2 * sprite.getScaleY());

        // Update origin of sprite based off of box2d body
        sprite.setOrigin(body.getPosition().x, body.getPosition().y);

        // Change sprite's size based off of current frame
        sprite.setSize(sprite.getRegionWidth(), sprite.getRegionHeight());

        animationTimer += dt;
    }
}
