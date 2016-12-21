package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends Sprite {

    private TextureAtlas atlas;

    private Animation walkAnimation;
    private Animation swordWalkAnimation;

    private TextureRegion idle;
    private TextureRegion weaponIdle;

    private float elapsedTime = 0;

    private enum State {
        RIGHT,
        LEFT,
        STILL,
    }

    private State previous;
    private State current;

    private boolean isHoldingSword = false;

    public Player(TextureAtlas atlas) {
        this.atlas = atlas;

        walkAnimation = new Animation(1 / 11f, atlas.findRegions("walk/walk"));
        swordWalkAnimation = new Animation(1 / 11f, atlas.findRegions("sword/sword"));

        idle = atlas.findRegion("walk/walk_idle");
        weaponIdle = atlas.findRegion("sword/sword_idle");

        current = State.STILL;
    }

    public void update(float dt) {

        handleInput();


        if (previous != current) {
            elapsedTime = 0;
        }


        if (!isHoldingSword) {
            switch (current) {
                case STILL:
                default:
                    setRegion(idle);
                    setBounds(0, 0, idle.getRegionWidth(), idle.getRegionHeight());
                    break;
                case RIGHT:
                case LEFT:
                    TextureRegion frame = walkAnimation.getKeyFrame(elapsedTime, true);
                    setRegion(frame);
                    setBounds(0, 0, frame.getRegionWidth(), frame.getRegionHeight());
                    break;
            }
        }

        else {
            switch (current) {
                case STILL:
                default:
                    setRegion(weaponIdle);
                    setBounds(0, 0, weaponIdle.getRegionWidth(), weaponIdle.getRegionHeight());
                    break;
                case RIGHT:
                case LEFT:
                    TextureRegion frame = swordWalkAnimation.getKeyFrame(elapsedTime, true);
                    setRegion(frame);
                    setBounds(0, 0, frame.getRegionWidth(), frame.getRegionHeight());
                    break;
            }
        }

        elapsedTime += dt;

        switch (current) {
            case LEFT:
                setFlip(true, false);
                break;
            default:
            case STILL:
            case RIGHT:
                setFlip(false, false);
                break;
        }

    }

    private void handleInput() {
        previous = current;

        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            isHoldingSword = !isHoldingSword;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            current = State.LEFT;
        }

        else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            current = State.RIGHT;
        }

        else {
            current = State.STILL;
        }

    }

}
