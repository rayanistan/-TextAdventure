package com.rayanistan.game.systems;

import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.physics.box2d.*;
import com.rayanistan.game.events.CollisionEvent;
import com.rayanistan.game.utils.ContactAdapter;

public class CollisionSystem extends ContactAdapter {

    private Signal<CollisionEvent> collisionSignal;

    public CollisionSystem(World world) {
        world.setContactListener(this);
        this.collisionSignal = new Signal<>();
    }

    @Override
    public void beginContact(Contact contact) {
        collisionSignal.dispatch(new CollisionEvent(contact.getFixtureA(),
                contact.getFixtureB(), CollisionEvent.Type.BEGIN_CONTACT));
    }

    @Override
    public void endContact(Contact contact) {
        collisionSignal.dispatch(new CollisionEvent(contact.getFixtureA(),
                contact.getFixtureB(), CollisionEvent.Type.END_CONTACT));
    }

    public void add(Listener<CollisionEvent> listener) {
        collisionSignal.add(listener);
    }
}