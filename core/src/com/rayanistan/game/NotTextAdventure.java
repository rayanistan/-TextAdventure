package com.rayanistan.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.rayanistan.game.handlers.GameStateManager;

public class NotTextAdventure extends Game {

    // Application Constant
    public static final int WIDTH = 720;
    public static final int HEIGHT = 480;
    public static final String TITLE = "~TextAdventure";

    // DEBUG
    public static boolean DEBUG = true;

    // Declare batches for drawing
    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;

    // Declare managers
    private GameStateManager gsm;
    public AssetManager assets;


    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        assets = new AssetManager();
        gsm = new GameStateManager(this);
        gsm.setState(GameStateManager.State.LOADING);
    }

    @Override
    public void render() {
        super.render();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SEMICOLON)) DEBUG = !DEBUG;
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
        assets.dispose();
        gsm.dispose();
    }



}

