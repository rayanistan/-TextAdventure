package com.rayanistan.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.rayanistan.game.components.PlayerComponent;
import com.rayanistan.game.interfaces.CollisionListener;
import com.rayanistan.game.utils.Mappers;

public class PlayerCollisionSystem extends EntitySystem implements CollisionListener {

    private PlayerComponent player;

    public PlayerCollisionSystem() {
        super(-1);
    }

    @Override
    public void addedToEngine(Engine engine) {
        player = Mappers.playerMapper.get(engine.getEntitiesFor(Family.all(PlayerComponent.class).get()).get(0));
    }

    @Override
    public void onBeginContact(Fixture a, Fixture b) {
        if (a != null && b != null) {
            if (a.getUserData().equals("footSensor") || b.getUserData().equals("footSensor")) {
                player.numOfContactsWithFoot++;
            }
        }
    }

    @Override
    public void onEndContact(Fixture a, Fixture b) {
        if (a != null && b != null) {
            if (a.getUserData().equals("footSensor") || b.getUserData().equals("footSensor")) {
                player.numOfContactsWithFoot--;
            }
        }
    }
}
