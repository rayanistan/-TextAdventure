package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.entities.Player;
import com.mygdx.game.handlers.GameStateManager;
import com.mygdx.game.utils.CameraUtils;

/**
 * Created by creat on 12/20/2016.
 */
public class PlayState extends AbstractState {

    private TextureAtlas atlas;
    private Player player;

    public PlayState(GameStateManager gsm) {
        super(gsm);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        cam.setToOrtho(false, width / 2, height / 2);

        atlas = new TextureAtlas(Gdx.files.internal("atlas.atlas"));
        player = new Player(atlas);
    }

    @Override
    public void update(float dt) {
        // Update camera
        cameraUpdate();

        // Update player
        player.update(dt);
    }

    private void cameraUpdate() {
        Vector2 target = new Vector2(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2);
        CameraUtils.lerpToTarget(cam, target);
    }

    @Override
    public void draw() {
        Gdx.gl.glClearColor(255, 255, 255, 255);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        app.batch.setProjectionMatrix(cam.combined);

        app.batch.begin();
        player.draw(app.batch);
        app.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void dispose() {

    }
}
