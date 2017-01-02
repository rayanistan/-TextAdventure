package com.rayanistan.game.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class TiledRenderingSystem extends EntitySystem {

    private OrthogonalTiledMapRenderer mapRenderer;
    private OrthographicCamera camera;

    public TiledRenderingSystem(TiledMap map, OrthographicCamera camera) {
        this.camera = camera;
        mapRenderer = new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        mapRenderer.setView(camera);
        mapRenderer.render();
    }
}
