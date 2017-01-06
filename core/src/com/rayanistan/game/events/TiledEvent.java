package com.rayanistan.game.events;

public abstract class TiledEvent {

    Type t;

    enum Type {
        GAME_OVER,
        SPAWN
    }

    TiledEvent(Type t) {
        this.t = t;
    }
}
