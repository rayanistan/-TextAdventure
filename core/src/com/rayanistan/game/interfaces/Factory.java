package com.rayanistan.game.interfaces;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;

public interface Factory {
    Entity spawnEntity(PooledEngine engine);
}
