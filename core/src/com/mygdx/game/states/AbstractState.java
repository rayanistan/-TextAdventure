package com.mygdx.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.mygdx.game.NotTextAdventure;
import com.mygdx.game.handlers.GameStateManager;

/**
 * Created by creat on 12/20/2016.
 */
public abstract class AbstractState {

    protected OrthographicCamera cam;
    protected NotTextAdventure app;
    protected GameStateManager gsm;

    protected AbstractState(final GameStateManager gsm) {
        this.gsm = gsm;
        this.app = gsm.getApp();
        cam = new OrthographicCamera();
    }

    public abstract void update(float dt);
    public abstract void draw();
    public  void resize(int width, int height) {
        cam.setToOrtho(false, width, height);
    }
    public abstract void dispose();
}
