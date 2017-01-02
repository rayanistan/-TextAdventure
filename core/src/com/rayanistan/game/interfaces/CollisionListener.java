package com.rayanistan.game.interfaces;

import com.badlogic.gdx.physics.box2d.Fixture;

public interface CollisionListener {
    void onBeginContact(Fixture a, Fixture b);
    void onEndContact(Fixture a, Fixture b);
}
