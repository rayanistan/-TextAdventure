package com.rayanistan.game.handlers;

import com.rayanistan.game.NotTextAdventure;
import com.rayanistan.game.states.AbstractState;
import com.rayanistan.game.states.LoadingState;
import com.rayanistan.game.states.PlayState;

import java.util.Stack;

public class GameStateManager {

    public enum State {
        PLAY {
            public AbstractState getState(final GameStateManager gsm) {
               return new PlayState(gsm);
            }
        },
        LOADING {
            public AbstractState getState(final GameStateManager gsm) {return new LoadingState(gsm); }
        };

        public abstract AbstractState getState(final GameStateManager gsms);
    }

    // Have context reference to app
    private NotTextAdventure app;

    // Have a stack of AbstractStates
    private Stack<AbstractState> states;

    public GameStateManager(final NotTextAdventure app) {
        this.app = app;
        states = new Stack<AbstractState>();
    }

    public void setState(State state) {
        if (!states.empty()) {
            states.pop().dispose();
        }

        states.add(getState(state));
    }

    public void dispose() {
        for (AbstractState state : states) {
            state.dispose();
        }
    }

    private AbstractState getState(State state) {
        return state.getState(this);
    }

    public void update(float dt) {
        states.peek().update(dt);
    }

    public void resize(int w, int h){
        states.peek().resize(w, h);
    }

    public void draw() {
        states.peek().draw();
    }

    public NotTextAdventure getApp() {
        return app;
    }

}
