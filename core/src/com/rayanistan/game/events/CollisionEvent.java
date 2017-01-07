package com.rayanistan.game.events;

import com.badlogic.gdx.physics.box2d.Fixture;

public class CollisionEvent {

    public Fixture a;
    public Fixture b;
    public Type t;

    public enum Type {
        BEGIN_CONTACT,
        END_CONTACT
    }

    public CollisionEvent(Fixture a, Fixture b, Type t) {
        this.a = a;
        this.b = b;
        this.t = t;
    }
}
