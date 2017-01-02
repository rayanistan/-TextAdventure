package com.rayanistan.game.utils;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by creat on 12/28/2016.
 */
public class WorldContactListener implements ContactListener {

    private int numOfContacts;

    public int getNumOfContacts() {
        return numOfContacts;
    }

    public WorldContactListener() {
        numOfContacts = 0;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getUserData() != null && fb.getUserData() != null) {
            if (fa.getUserData().equals("player-foot") || fb.getUserData().equals("player-foot")) {
                numOfContacts++;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();

        if (fa.getUserData() != null && fb.getUserData() != null) {
            if (fa.getUserData().equals("player-foot") || fb.getUserData().equals("player-foot")) {
                numOfContacts--;
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
