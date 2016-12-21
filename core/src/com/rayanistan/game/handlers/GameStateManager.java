package com.rayanistan.game.handlers;

import com.rayanistan.game.NotTextAdventure;
import com.rayanistan.game.states.AbstractState;
import com.rayanistan.game.states.LoadingState;
import com.rayanistan.game.states.PlayState;

import java.util.HashMap;

public class GameStateManager {

    private NotTextAdventure app;

    public NotTextAdventure getApp() {
        return app;
    }

    public enum State {
        LOADING,
        PLAY
    }

    private HashMap<State, AbstractState> states;

    public GameStateManager(final NotTextAdventure app) {
        this.app = app;
        states = new HashMap<State, AbstractState>();

        initAbstractStates();
    }

    private void initAbstractStates() {
        states.put(State.LOADING, new LoadingState(this));
        states.put(State.PLAY, new PlayState(this));
    }

    public void setState(State state) {
        AbstractState abState = states.get(state);

        app.setScreen(abState);
    }

    public void dispose() {
        for (AbstractState abState : states.values()) {
            abState.dispose();
        }

        states.clear();
    }

}
