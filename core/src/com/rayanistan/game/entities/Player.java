package com.rayanistan.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.rayanistan.game.states.PlayState;
import com.rayanistan.game.utils.WorldUtils;

import static com.rayanistan.game.utils.WorldUtils.Constants.PPM;

public final class Player {

    private Sprite sprite;
    private Body body;
    private PlayState state;

    private Animation walkAnimation;
    private Animation swordWalkAnimation;

    private TextureRegion idle;
    private TextureRegion weaponIdle;

    private float animationTimer = 0;

    private enum State {
        RIGHT,
        LEFT,
        STILL,
    }

    private State previous;
    private State current;

    private boolean isHoldingSword = false;

    public Player(PlayState state) {

        this.state = state;

        walkAnimation = new Animation(1 / 11f, state.atlas.findRegions("walk/walk"));
        swordWalkAnimation = new Animation(1 / 11f, state.atlas.findRegions("sword/sword"));

        idle = state.atlas.findRegion("walk/walk_idle");
        weaponIdle = state.atlas.findRegion("sword/sword_idle");

        current = State.STILL;
        previous = State.STILL;

        sprite = new Sprite(idle);

        initBody();
    }

    private void initBody() {
        this.body = WorldUtils.createBox(state.world, 32, 32, 32,
                32, false, sprite);
    }

    public void update(float dt) {

        handleInput(dt);

        handleAnimation(dt);

        sprite.setPosition(body.getPosition().x * PPM - sprite.getWidth() / 2,
                body.getPosition().y * PPM - sprite.getHeight() / 2);

    }

    private void handleAnimation(float dt) {
        if (previous != current) {
            animationTimer = 0;
        }


        if (!isHoldingSword) {
            switch (current) {
                case STILL:
                default:
                    sprite.setRegion(idle);
                    sprite.setBounds(0, 0, idle.getRegionWidth(), idle.getRegionHeight());
                    break;
                case RIGHT:
                case LEFT:
                    TextureRegion frame = walkAnimation.getKeyFrame(animationTimer, true);
                    sprite.setRegion(frame);
                    sprite.setBounds(0, 0, frame.getRegionWidth(), frame.getRegionHeight());
                    break;
            }
        }

        else {
            switch (current) {
                case STILL:
                default:
                    sprite.setRegion(weaponIdle);
                    sprite.setBounds(0, 0, weaponIdle.getRegionWidth(), weaponIdle.getRegionHeight());
                    break;
                case RIGHT:
                case LEFT:
                    TextureRegion frame = swordWalkAnimation.getKeyFrame(animationTimer, true);
                    sprite.setRegion(frame);
                    sprite.setBounds(0, 0, frame.getRegionWidth(), frame.getRegionHeight());
                    break;
            }
        }

        animationTimer += dt;

        switch (current) {
            case LEFT:
                sprite.setFlip(true, false);
                break;
            default:
            case STILL:
            case RIGHT:
                sprite.setFlip(false, false);
                break;
        }
    }

    private void handleInput(float dt) {
        previous = current;

        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            isHoldingSword = !isHoldingSword;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            current = State.LEFT;

            body.setLinearVelocity(-350 * dt , body.getLinearVelocity().y);
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            current = State.RIGHT;

            body.setLinearVelocity(350 * dt, body.getLinearVelocity().y);
        }

        else {
            current = State.STILL;

            body.setLinearVelocity(0, body.getLinearVelocity().y);
        }
    }

    public void render(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public Vector2 getCenter() {
        return new Vector2(body.getPosition().x * PPM, body.getPosition().y * PPM);
    }
}
