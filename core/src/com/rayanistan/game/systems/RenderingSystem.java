package com.rayanistan.game.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.rayanistan.game.components.TextureComponent;
import com.rayanistan.game.components.TransformComponent;
import com.rayanistan.game.utils.GameCamera;
import com.rayanistan.game.utils.Mappers;

import java.util.Comparator;

public class RenderingSystem extends SortedIteratingSystem {

    private SpriteBatch batch;
    private Array<Entity> renderQueue;
    private GameCamera camera;

    public RenderingSystem(SpriteBatch batch, GameCamera camera) {
        super(Family.all(TransformComponent.class, TextureComponent.class).get(),
                new ZComparator());

        this.batch = batch;
        this.camera = camera;
        this.renderQueue = new Array<Entity>();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        renderQueue.sort(new ZComparator());

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (Entity e : renderQueue) {
            TextureComponent texture = Mappers.textureMapper.get(e);
            TransformComponent tc = Mappers.transformMapper.get(e);

            if (texture.region == null) {
                continue;
            }

            float w = texture.region.getRegionWidth();
            float h = texture.region.getRegionHeight();

            float oX = w / 2;
            float oY = h / 2;

            batch.draw(texture.region,tc.pos.x - oX,
                    tc.pos.y - oY, oX, oY, w, h,
                    tc.scale.x, tc.scale.y, tc.rotation);
        }

        batch.end();

        renderQueue.clear();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        renderQueue.add(entity);
    }

    private static class ZComparator implements Comparator<Entity> {
        @Override
        public int compare(Entity e1, Entity e2) {
            return (int) Math.signum(Mappers.transformMapper.get(e1).pos.z
                    - Mappers.transformMapper.get(e1).pos.z);
        }
    }
}
