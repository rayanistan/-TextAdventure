package com.rayanistan.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.rayanistan.game.components.SpriteComponent;
import com.rayanistan.game.utils.Mappers;


public class SpriteRenderingSystem extends IteratingSystem {

    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Array<SpriteComponent> renderQueue;

    public SpriteRenderingSystem(SpriteBatch batch, OrthographicCamera camera) {
        super(Family.all(SpriteComponent.class).get());

        this.batch = batch;

        this.camera = camera;

        this.camera.setToOrtho(false);
        renderQueue = new Array<SpriteComponent>();
    }

    @Override
    public void update(float dt) {

        super.update(dt);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        for (SpriteComponent s : renderQueue) {
            s.sprite.draw(batch);

        }
        batch.end();

        renderQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        SpriteComponent sprite = Mappers.spriteMapper.get(entity);
        renderQueue.add(sprite);
    }
}
