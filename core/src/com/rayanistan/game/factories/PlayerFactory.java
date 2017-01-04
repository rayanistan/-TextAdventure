package com.rayanistan.game.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.rayanistan.game.components.*;
import com.rayanistan.game.utils.Assets;
import com.rayanistan.game.utils.BodyUtils;

public class PlayerFactory {

    public static Entity spawnEntity(PooledEngine engine, Vector2 position) {
        Entity player = engine.createEntity();

        // Create Components
        AnimationComponent animation         = engine.createComponent(AnimationComponent.class);
        SpriteComponent sprite               = engine.createComponent(SpriteComponent.class);
        BodyComponent body                   = engine.createComponent(BodyComponent.class);
        PlayerComponent component            = engine.createComponent(PlayerComponent.class);
        TransformComponent transform         = engine.createComponent(TransformComponent.class);
        FocalPointComponent focalPoint       = engine.createComponent(FocalPointComponent.class);

        animation.animations.put(AnimationComponent.State.IDLING, Assets.getPlayerIdling());
        animation.animations.put(AnimationComponent.State.WALKING, Assets.getPlayerWalking());
        animation.animations.put(AnimationComponent.State.JUMPING, Assets.getPlayerJumping());

        // Create sprite by getting first frame of idle
        sprite.sprite.setRegion(animation.getFrame());
        sprite.sprite.setSize(sprite.sprite.getRegionWidth(), sprite.sprite.getRegionHeight());
        sprite.sprite.setPosition(position.x, position.y);

        transform.scale = new Vector2(1f, 1f);
        sprite.sprite.setScale(transform.scale.x, transform.scale.y);

        // Generate body by using readFromJson
        Vector2 spriteCenter = new Vector2(sprite.sprite.getX() + sprite.sprite.getWidth() / 2,
                sprite.sprite.getY() + sprite.sprite.getHeight() / 2);

        body.body = BodyUtils.readFromJson(Gdx.files.internal("settings/playerInfo.json"), spriteCenter);


        // Set focalPoint.current to true
        focalPoint.current = true;

        // Add components
        player.add(focalPoint);
        player.add(animation);
        player.add(sprite);
        player.add(body);
        player.add(component);
        player.add(transform);

        engine.addEntity(player);

        return player;
    }
}
