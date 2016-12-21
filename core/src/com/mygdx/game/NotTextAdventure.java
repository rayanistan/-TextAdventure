package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.mygdx.game.handlers.GameStateManager;

public class NotTextAdventure extends ApplicationAdapter {

    // Declare batches for drawing
    public SpriteBatch batch;

    // Declare managers
    private GameStateManager gsm;


    @Override
    public void create() {
        batch = new SpriteBatch();
        gsm = new GameStateManager(this);
        gsm.setState(GameStateManager.State.PLAY);
    }


    @Override
    public void render() {
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.draw();
    }

    @Override
    public void resize(int width, int height) {
        gsm.resize(width / 2, height / 2);
    }


    @Override
    public void dispose() {
        gsm.dispose();
        batch.dispose();
    }


}

