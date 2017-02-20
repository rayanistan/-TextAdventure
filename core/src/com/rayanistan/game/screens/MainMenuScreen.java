package com.rayanistan.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.rayanistan.game.NotTextAdventure;

/**
 * Created by Rayanistan on 2/18/17.
 */
public class MainMenuScreen extends ScreenAdapter {

    private NotTextAdventure app;

    public MainMenuScreen(final NotTextAdventure app) {


    }

    @Override
    public void render(float delta) { // update and draw stuff if (Gdx.input.justTouched()) // use your own criterion here game.setScreen(game.anotherScreen); }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override public void show() { // called when this screen is set as the screen with game.setScreen();


    }
    @Override public void hide() { // called when current screen changes from this to a different screen

    }

    @Override public void pause() {


    }
    @Override public void resume() {

    }

    @Override public void dispose(){


        }
}
