package com.rayanistan.game.factories;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.rayanistan.game.components.AnimationComponent;
import com.rayanistan.game.components.SpriteComponent;
import com.rayanistan.game.components.TransformComponent;
import com.rayanistan.game.interfaces.Factory;

public class WizardFactory implements Factory {

    private AssetManager assets;
    private Vector2 position;

    public WizardFactory(AssetManager assets, Vector2 position) {
        this.assets = assets;
        this.position = position;
    }

    public Entity buildEntity() {
        Entity wizard = new Entity();

        // Create components
        AnimationComponent animation = new AnimationComponent();
        SpriteComponent sprite = new SpriteComponent();
        TransformComponent transform = new TransformComponent();

        // Create idle animation
        Animation idle = new Animation(0.75f, assets.get("sprites/npc.atlas",
                TextureAtlas.class).findRegions("w"));

        animation.animations.put(AnimationComponent.State.IDLING, idle);
        animation.setAnimation(AnimationComponent.State.IDLING, true);

        sprite.sprite.setRegion(animation.getFrame());
        sprite.sprite.setSize(sprite.sprite.getRegionWidth(), sprite.sprite.getRegionHeight());

        transform.scale = new Vector2(2.2f, 2.2f);
        sprite.sprite.setScale(transform.scale.x, transform.scale.y);
        sprite.sprite.setPosition(position.x, position.y);

        wizard.add(animation);
        wizard.add(sprite);
        wizard.add(transform);

        return wizard;
    }

    public static Entity spawnEntity(AssetManager assets, Vector2 position) {
        return (new WizardFactory(assets, position)).buildEntity();
    }

    @Override
    public Entity spawnEntity(PooledEngine engine) {
        return null;
    }
}
