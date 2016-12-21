package com.rayanistan.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.rayanistan.game.NotTextAdventure;
import com.rayanistan.game.handlers.GameStateManager;

public abstract class AbstractState implements Screen {

    protected OrthographicCamera cam;
    protected NotTextAdventure app;
    protected GameStateManager gsm;

    protected AbstractState(final GameStateManager gsm) {
        this.gsm = gsm;
        this.app = gsm.getApp();
        cam = new OrthographicCamera();
    }

    public abstract void update(float dt);

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl20.glClearColor(1f,1f, 1f, 1f);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        cam.setToOrtho(false, width / 2, height / 2);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    public abstract void dispose();
}
