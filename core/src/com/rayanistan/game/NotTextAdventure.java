package com.rayanistan.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.rayanistan.game.handlers.GameStateManager;

public class NotTextAdventure extends Game {

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
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        assets.dispose();
        gsm.dispose();
    }



}

