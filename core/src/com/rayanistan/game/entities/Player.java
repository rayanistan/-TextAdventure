package com.rayanistan.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.rayanistan.game.states.PlayState;
import com.rayanistan.game.utils.WorldUtils;

import static com.rayanistan.game.utils.WorldUtils.Constants.PPM;

public final class Player {

    private Sprite sprite;
    private Body body;
    private PlayState state;

    private float animationTimer = 0;

    private enum State {
        NOTHING {
            public TextureRegion getFrame(TextureAtlas atlas, float timer) {
                return new Animation(1 / 11f, atlas.findRegions("walk")).getKeyFrame(timer, true);
            }
        },
        SWORD {
            public TextureRegion getFrame(TextureAtlas atlas, float timer) {
                return new Animation(1 / 11f, atlas.findRegions("s")).getKeyFrame(timer, true);
            }
        },
        NOTHING_IDLE {
            public TextureRegion getFrame(TextureAtlas atlas, float timer) {
                return new Animation(5/3f, atlas.findRegions("nothing_idle")).getKeyFrame(timer, true);
            }
        },
        SWORD_IDLE {
            public TextureRegion getFrame(TextureAtlas atlas, float timer) {
                return new Animation(5/3f, atlas.findRegions("sword_idle")).getKeyFrame(timer, true);
            }
        };

        public abstract TextureRegion getFrame(TextureAtlas atlas, float timer);
    }

    private State previous;
    private State current;

    private boolean flipped = false;

    public Player(PlayState state) {

        this.state = state;

        current = State.NOTHING;
        previous = current;


        sprite = new Sprite();

        this.body = WorldUtils.createBox(state.world, 32, 32, 32,
                32, false, sprite);
    }

    public void update(float dt) {

        handleInput(dt);

        handleAnimation(dt);

        sprite.setPosition(body.getPosition().x * PPM - sprite.getRegionHeight() / 2,
                body.getPosition().y * PPM - sprite.getRegionHeight() / 2 - 6);

    }

    private void handleAnimation(float dt) {
        if (previous != current) {
            animationTimer = 0;
        }

        sprite.setRegion(current.getFrame(state.atlas, animationTimer));

        sprite.setBounds(0, 0, sprite.getRegionWidth(), sprite.getRegionHeight());

        animationTimer += dt;

        sprite.setFlip(flipped, false);

    }

    private void handleInput(float dt) {
        previous = current;

        // MOVEMENT
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            flipped = true;
            body.setLinearVelocity(-350 * dt, body.getLinearVelocity().y);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            flipped = false;
            body.setLinearVelocity(350 * dt, body.getLinearVelocity().y);
        } else {
            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }


        // JUMPING
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            body.applyForceToCenter(0, 300, true);
        }


        // SWITCHING BETWEEN SWORD AND NON SWORD
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            current = current == State.NOTHING || previous == State.NOTHING_IDLE ? State.SWORD : State.NOTHING;
        }

        // IF IDLE SWITCH TO IDLE VERSION OF STATE
        if (body.getLinearVelocity().x == 0) {
            switch (current) {
                case SWORD:
                    current = State.SWORD_IDLE;
                    break;
                case NOTHING:
                    current = State.NOTHING_IDLE;
            }
        }
        else {
            switch (current) {
                case SWORD_IDLE:
                    current = State.SWORD;
                    break;
                case NOTHING_IDLE:
                    current = State.NOTHING;
                    break;
            }
        }


    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Vector2 getCenter() {
        return new Vector2(body.getPosition().x * PPM, body.getPosition().y * PPM);
    }
}
