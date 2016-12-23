package com.rayanistan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.rayanistan.game.NotTextAdventure;

import static com.rayanistan.game.NotTextAdventure.SCALE;

// AbstractState implements from LibGDX's Screen to implement useless methods
public abstract class AbstractState implements Screen {

    // TODO: Make camera a public property in NotTextAdventure.java
    // TODO: Have each state have a Scene2D Stage instead

    // OrthographicCamera is used to define the viewport in which the player can see
    protected OrthographicCamera cam;

    // Context Reference to text adventure
    protected NotTextAdventure app;

    // Protected constructor
    protected AbstractState(final NotTextAdventure app) {
        this.app = app;
        cam = new OrthographicCamera();
    }

    // Update logic should be called before rendering anything to the screen
    public abstract void update(float dt);

    // Calls this method every time the screen is about to be displayed
    public abstract void show();

    // Resources must be disposed of to avoid memory leakage
    public abstract void dispose();

    // Render method is called every frame to draw to the screen
    public void render(float delta) {
        update(delta);

        // Clear the screen with white
        Gdx.gl20.glClearColor(1f,1f, 1f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    // Resize viewport/camera if the window is resized
    public void resize(int width, int height) {
        cam.setToOrtho(false, width / SCALE, height / SCALE);
    }

    // Useless methods that are implemented to save space in other classes
    public void pause() {}
    public void resume() {}
    public void hide() {}

}
