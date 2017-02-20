package com.rayanistan.game.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.rayanistan.game.components.*;
import com.rayanistan.game.utils.Assets;
import javafx.animation.Animation;

/**
 * Created by Rayanistan on 1/21/17.
 */
public class GunsmithFactory implements Factory {


    public static Entity spawnEntity(PooledEngine engine, Vector2 position){

        Entity gunsmith = engine.createEntity();

        AnimationComponent animation = engine.createComponent(AnimationComponent.class);
        SpriteComponent sprite       = engine.createComponent(SpriteComponent.class);
        TransformComponent transform = engine.createComponent(TransformComponent.class);
        GunsmithComponent component  = engine.createComponent(GunsmithComponent.class);
        FocalPointComponent focalPoint       = engine.createComponent(FocalPointComponent.class);


        animation.animations.put(GunsmithComponent.State.IDLING, Assets.getGunsmithIdling());
        animation.setAnimation(GunsmithComponent.State.IDLING);

        sprite.sprite.setRegion(animation.getFrame());
        sprite.sprite.setSize(sprite.sprite.getRegionWidth(), sprite.sprite.getRegionHeight());

        transform.scale = new Vector2(2.0f, 2.2f);
        transform.rotation = 60;
        sprite.sprite.setScale(transform.scale.x, transform.scale.y);
        sprite.sprite.setPosition(position.x, position.y);

        gunsmith.add(sprite);
        gunsmith.add(animation);
        gunsmith.add(transform);
        gunsmith.add(component);
        gunsmith.add(focalPoint);
        engine.addEntity(gunsmith);

        return gunsmith;


    }

}
