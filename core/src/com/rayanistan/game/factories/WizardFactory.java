package com.rayanistan.game.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.math.Vector2;
import com.rayanistan.game.components.*;
import com.rayanistan.game.utils.Assets;

public class WizardFactory implements Factory {

    public static Entity spawnEntity(PooledEngine engine, Vector2 position) {
        Entity wizard = engine.createEntity();

        // Create components
        AnimationComponent animation       = engine.createComponent(AnimationComponent.class);
        SpriteComponent sprite             = engine.createComponent(SpriteComponent.class);
        TransformComponent transform       = engine.createComponent(TransformComponent.class);
        WizardComponent component          = engine.createComponent(WizardComponent.class);

        animation.animations.put(WizardComponent.State.IDLING, Assets.getWizardIdling());
        animation.setAnimation(WizardComponent.State.IDLING);

        sprite.sprite.setRegion(animation.getFrame());
        sprite.sprite.setSize(sprite.sprite.getRegionWidth(), sprite.sprite.getRegionHeight());

        transform.scale = new Vector2(2.2f, 2.2f);
        transform.rotation = 60;
        sprite.sprite.setScale(transform.scale.x, transform.scale.y);
        sprite.sprite.setPosition(position.x, position.y);

        wizard.add(component);
        wizard.add(animation);
        wizard.add(sprite);
        wizard.add(transform);

        engine.addEntity(wizard);

        return wizard;
    }
}
