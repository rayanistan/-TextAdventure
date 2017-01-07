package com.rayanistan.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.signals.Signal;
import com.rayanistan.game.components.PlayerComponent;
import com.rayanistan.game.events.CollisionEvent;
import com.rayanistan.game.utils.Mappers;

public class PlayerCollisionSystem extends EntitySystem implements Listener<CollisionEvent> {

    private PlayerComponent player;

    public PlayerCollisionSystem() {
        super(-1);
    }

    @Override
    public void addedToEngine(Engine engine) {
        player = Mappers.playerMapper.get(engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0));
    }

    @Override
    public void receive(Signal<CollisionEvent> signal, CollisionEvent event) {
        if (event.a != null && event.b != null) {
            switch (event.t) {
                case BEGIN_CONTACT:
                    if (event.a.getUserData().equals("footSensor") || event.b.getUserData().equals("footSensor")) {
                        player.numOfContactsWithFoot++;
                    }
                    break;
                case END_CONTACT:
                    if (event.a.getUserData().equals("footSensor") || event.b.getUserData().equals("footSensor")) {
                        player.numOfContactsWithFoot--;
                    }
                    break;
            }
        }
    }
}
