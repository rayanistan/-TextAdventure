package com.rayanistan.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rayanistan.game.handlers.GameStateManager;

public class NotTextAdventure extends ApplicationAdapter {

    // Declare batches for drawing
    public SpriteBatch batch;

    // Declare managers
    private GameStateManager gsm;
    public AssetManager assets;


    @Override
    public void create() {
        batch = new SpriteBatch();

        assets = new AssetManager();
        gsm = new GameStateManager(this);
        gsm.setState(GameStateManager.State.LOADING);
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
        assets.dispose();
        batch.dispose();
    }


}

