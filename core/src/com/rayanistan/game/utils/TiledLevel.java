package com.rayanistan.game.utils;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.ashley.signals.Signal;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.rayanistan.game.events.SpawnEvent;
import com.rayanistan.game.events.TiledEvent;
import com.rayanistan.game.factories.Factory;

import java.lang.reflect.Method;

import static com.rayanistan.game.utils.Constants.PPM;

public class TiledLevel implements Disposable {

    public TiledMap map;
    private PooledEngine engine;

    private Signal<TiledEvent> eventSignal;

    private MapLayer collisionLayer, eventLayer;

    private int tileWidth, tileHeight, levelWidth, levelHeight;

    public TiledLevel(TiledMap map, PooledEngine engine) {
        this.map = map;
        this.engine = engine;

        eventSignal = new Signal<>();

        MapProperties props = this.map.getProperties();

        this.tileWidth = props.get("tilewidth", Integer.class);
        this.tileHeight = props.get("tileheight", Integer.class);
        this.levelWidth = props.get("width", Integer.class);
        this.levelHeight = props.get("height", Integer.class);

        this.collisionLayer = this.map.getLayers().get(props.get("collisionlayer", String.class));
        this.eventLayer = this.map.getLayers().get(props.get("eventlayer", String.class));
    }

    public void parseCollisionLayer() {
        for (MapObject object : collisionLayer.getObjects()) {

            if (object instanceof RectangleMapObject) {
                Rectangle bounds = ((RectangleMapObject) object).getRectangle();

                BodyUtils.createFromRectangle(bounds).getFixtureList().first().setUserData(object);
            }

            if (object instanceof PolylineMapObject) {
                float[] vertices = ((PolylineMapObject) object).getPolyline().getTransformedVertices();
                Vector2[] verticesVector2 = new Vector2[vertices.length / 2];

                for (int i = 0; i < verticesVector2.length; i++) {
                    verticesVector2[i] = new Vector2(vertices[i * 2] / PPM, vertices[i * 2 + 1] / PPM);
                }

                BodyUtils.createFromVertices(verticesVector2).getFixtureList().first().setUserData(object);
            }
        }
    }

    public void fireSpawnEvents() {
        for (MapObject object : eventLayer.getObjects()) {

            MapProperties props = object.getProperties();

            if (props.get("type").equals("Spawn")) {
                try { // Use reflection to fetch factory from entities of type Spawn in Tiled
                    Class<Factory> c = ClassReflection.forName(String.valueOf(props.get("factory")));
                    Method m = c.getMethod("spawnEntity", PooledEngine.class, Vector2.class);

                    Vector2 spawnPoint = new Vector2(props.get("x", Float.class), props.get("y", Float.class));
                    m.invoke(null, engine, spawnPoint);

                    // Using observable pattern, dispatch a new SpawnEvent with a Class<Factory>
                    eventSignal.dispatch(new SpawnEvent(c));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Vector2 getDimensionsInPixels() {
        return new Vector2(tileWidth * levelWidth, tileHeight * levelHeight);
    }

    @Override
    public void dispose() {
        engine.clearPools();
        map.dispose();
    }
}
