package com.rayanistan.game.events;

import com.rayanistan.game.factories.Factory;

public class SpawnEvent extends TiledEvent {

    private Class<Factory> factory;

    public SpawnEvent(Class<Factory> factory) {
        super(Type.SPAWN);
        this.factory = factory;
    }
}