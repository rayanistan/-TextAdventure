package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import javax.xml.soap.Text;
import java.awt.*;

public class MyGdxGame extends ApplicationAdapter {
    private SpriteBatch batch;
    private Animation animation2;
    private TextureAtlas textureAtlas2;

    private TextureAtlas textureAtlas;
    private Animation animation;
    private float elapsedTime = 0;
    private BitmapFont font;
    private Sprite sprite;
    private Texture texture;

    private OrthographicCamera camera;

    private enum Direction {
        RIGHT,
        LEFT,
        STILL
    }

    private Direction direction;

    @Override
    public void create() {
        batch = new SpriteBatch();
//        textureAtlas = new TextureAtlas(Gdx.files.internal("walk-packed/pack.atlas"));
//        animation = new Animation(1 / 11f, textureAtlas.getRegions());

        textureAtlas2 = new TextureAtlas(Gdx.files.internal("walk2-packed/pack.atlas"));
        animation2 = new Animation(1 / 11f, textureAtlas2.getRegions());

        font = new BitmapFont();
        font.setColor(Color.WHITE);

        texture = new Texture(Gdx.files.internal("stand.png"));
        sprite = new Sprite(texture);
        direction = Direction.STILL;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
    }

    float x = 0;

    @Override
    public void render() {


        Gdx.gl.glClearColor(255, 255, 255, 255);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        elapsedTime += Gdx.graphics.getDeltaTime();

        Vector3 position = camera.position;
        position.x = MathUtils.lerp(position.x, sprite.getX() + sprite.getWidth() / 2, 0.2f);
        position.y = MathUtils.lerp(position.y, sprite.getY() + sprite.getHeight() / 2, 0.2f);
        camera.position.set(position);
        camera.update();


        batch.setProjectionMatrix(camera.combined);


        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            x += 2;
            TextureRegion frame = animation2.getKeyFrame(elapsedTime, true);
            sprite.setBounds(0, 0, frame.getRegionWidth(), frame.getRegionHeight());
            sprite.setRegion(frame);
            direction = Direction.RIGHT;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            x -= 2;
            TextureRegion frame = animation2.getKeyFrame(elapsedTime, true);
            sprite.setBounds(0, 0, frame.getRegionWidth(), frame.getRegionHeight());
            sprite.setRegion(frame);
            direction = Direction.LEFT;
        } else {
            sprite.setBounds(0, 0, texture.getWidth(), texture.getHeight());
            sprite.setRegion(texture);
            direction = Direction.STILL;
        }

        switch (direction) {
            default:
            case STILL:
                sprite.setFlip(false, false);
                break;
            case LEFT:
                sprite.setFlip(false, false);
                break;
            case RIGHT:
                sprite.setFlip(true, false);
                break;
        }

        sprite.setPosition(x, 170);

        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width / 2, height / 2);
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        texture.dispose();
        textureAtlas2.dispose();
        sprite.getTexture().dispose();
    }
}
