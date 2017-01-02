package com.rayanistan.game.systems;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.rayanistan.game.interfaces.CollisionListener;

public class CollisionSystem implements ContactListener {

    public Array<CollisionListener> collisionListeners;

    public CollisionSystem(World world) {
        world.setContactListener(this);
        this.collisionListeners = new Array<CollisionListener>();
    }

    @Override
    public void beginContact(Contact contact) {
        for (CollisionListener listener : collisionListeners) {
            listener.onBeginContact(contact.getFixtureA(), contact.getFixtureB());
        }
    }

    @Override
    public void endContact(Contact contact) {
        for (CollisionListener listener : collisionListeners) {
            listener.onEndContact(contact.getFixtureA(), contact.getFixtureB());
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
