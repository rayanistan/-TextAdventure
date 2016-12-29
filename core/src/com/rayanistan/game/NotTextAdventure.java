package com.rayanistan.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.rayanistan.game.screens.LoadingState;
import com.rayanistan.game.screens.PlayState;

public class NotTextAdventure extends Game {

    // Application constants to be used in the desktop launcher
    // Declares windows width + height as well as title
    public static final int WIDTH = 800;
    public static final int HEIGHT = 480;
    public static final String TITLE = "~TextAdventure";
    public static final float V_WIDTH = WIDTH / 2;
    public static final float V_HEIGHT = HEIGHT / 2;

    // A variable that screens if debug mode is draw, render the Box2D Debug lines
    public static boolean DEBUG = false;
    public static boolean RENDER = true;

    // Declare batches for drawing
    // Sprite/Shape Batches are used for displaying geometric figures/ textures on screen
    public SpriteBatch batch;
    public ShapeRenderer shapeRenderer;

    // Declare screens that will later be loaded
    public LoadingState loadingState;
    public PlayState playState;

    // Asset manager is used to loading assets in the beginning then referenced later
    public AssetManager assets;


    @Override
    public void create() {
        // Instantiate batches
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        // Instantiate Asset Manager
        assets = new AssetManager();

        // Instantiate screens
        loadingState = new LoadingState(this);
        playState = new PlayState(this);


        // Change state to loading
        this.setScreen(loadingState);

    }

    @Override
    public void dispose() {
        // Must dispose of heavy resources to avoid memory leakage
        shapeRenderer.dispose();
        batch.dispose();
        assets.dispose();
    }



}

