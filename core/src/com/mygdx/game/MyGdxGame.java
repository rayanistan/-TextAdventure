package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

public class MyGdxGame extends ApplicationAdapter {

    // Declare batches for drawing
    private SpriteBatch batch;

    // Declare animation + idle atlas + sprite + textures
    private Animation walkAnimation;
    private Animation swordWalkAnimation;
    private TextureAtlas atlas;
    private Sprite sprite;
    private TextureRegion idle;
    private TextureRegion weaponIdle;

    private float elapsedTime = 0;

    private OrthographicCamera camera;
    private boolean isEquipped = false;


    private enum Direction {
        RIGHT,
        LEFT,
        STILL
    }


    private Direction direction;

    @Override
    public void create() {
        batch = new SpriteBatch();

        atlas = new TextureAtlas(Gdx.files.internal("atlas.atlas"));
        walkAnimation = new Animation(1 / 11f, atlas.findRegions("walk/walk"));
        swordWalkAnimation = new Animation(1 / 11f, atlas.findRegions("sword/sword"));

        idle = atlas.findRegion("walk/walk_idle");
        sprite = new Sprite(idle);

        weaponIdle = atlas.findRegion("sword/sword_idle");

        direction = Direction.STILL;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    }


    int x = 0;

    @Override
    public void render() {


        Gdx.gl.glClearColor(255, 255, 255, 255);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        Vector3 position = camera.position;
        position.x = MathUtils.lerp(position.x, sprite.getX() + sprite.getWidth() / 2, 0.1f);
        position.y = MathUtils.lerp(position.y, sprite.getY() + sprite.getHeight() / 2, 0.1f);

        camera.position.set(position);
        camera.update();


        batch.setProjectionMatrix(camera.combined);


        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            isEquipped = !isEquipped;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += 2;
            if (!isEquipped) {
                TextureRegion frame = walkAnimation.getKeyFrame(elapsedTime, true);
                sprite.setBounds(frame.getRegionX(), frame.getRegionY(), frame.getRegionWidth(), frame.getRegionHeight());
                sprite.setRegion(frame);
            } else {
                TextureRegion frame = swordWalkAnimation.getKeyFrame(elapsedTime, true);
                sprite.setBounds(frame.getRegionX(), frame.getRegionY(), frame.getRegionWidth(), frame.getRegionHeight());
                sprite.setRegion(frame);
            }
            direction = Direction.RIGHT;

        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= 2;
            if (!isEquipped) {
                TextureRegion frame = walkAnimation.getKeyFrame(elapsedTime, true);
                sprite.setBounds(frame.getRegionX(), frame.getRegionY(), frame.getRegionWidth(), frame.getRegionHeight());
                sprite.setRegion(frame);
            } else {
                TextureRegion frame = swordWalkAnimation.getKeyFrame(elapsedTime, true);
                sprite.setBounds(frame.getRegionX(), frame.getRegionY(), frame.getRegionWidth(), frame.getRegionHeight());
                sprite.setRegion(frame);
            }
            direction = Direction.LEFT;
        } else {
            sprite.setBounds(0, 0, idle.getRegionWidth(), idle.getRegionHeight());
            sprite.setRegion(idle);

            if (isEquipped) {
                sprite.setBounds(0, 0, weaponIdle.getRegionWidth(), weaponIdle.getRegionHeight());
                sprite.setRegion(weaponIdle);
            }
        }


        //TODO: ^THE IF STATEMENT SHOULD CHANGE THE TEXTURE PERMANENTElY, UNTIL USER RE-CLICKS THE RIGHT BUTTON

        switch (direction) {
            default:
            case STILL:
                sprite.setFlip(false, false);
                break;
            case LEFT:
                sprite.setFlip(true, false);
                break;
            case RIGHT:
                sprite.setFlip(false, false);
                break;
        }


        sprite.setX(x);
        sprite.setY(170);


        batch.begin();
        sprite.draw(batch);
        batch.end();

        elapsedTime += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void resize(int width, int height) {

        camera.setToOrtho(false, width / 2, height / 2);
    }


    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
    }


}

