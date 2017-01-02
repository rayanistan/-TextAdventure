package com.rayanistan.game.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import static com.rayanistan.game.utils.Constants.PPM;

public class TiledLevel {

    public int levelID;

    public TiledMap tileMap;
    private MapLayer collisionLayer;
    private MapLayer eventLayer;

    private int tileWidth;
    private int tileHeight;

    private int levelWidth;
    private int levelHeight;

    public TiledLevel(int levelID) {
        this.levelID = levelID;
    }

    public void initialize(FileHandle jsonFile, AssetManager assets) {
        String rawJson = jsonFile.readString();

        JsonValue root = new JsonReader().parse(rawJson);

        this.tileMap = assets.get(root.getString("map"), TiledMap.class);

        MapProperties props = this.tileMap.getProperties();

        this.tileWidth = props.get("tilewidth", Integer.class);
        this.tileHeight = props.get("tileheight", Integer.class);
        this.levelWidth = props.get("width", Integer.class);
        this.levelHeight = props.get("height", Integer.class);

        this.collisionLayer = tileMap.getLayers().get(root.getString("collisionLayer"));
        this.eventLayer = tileMap.getLayers().get(root.getString("eventLayer"));
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
                    verticesVector2[i] = new Vector2(vertices[i * 2] / PPM , vertices[i * 2 + 1] / PPM);
                }

                BodyUtils.createFromVertices(verticesVector2).getFixtureList().first().setUserData(object);
            }
        }
    }

    public MapObjects getEvents() {
        return eventLayer.getObjects();
    }

    public Vector2 getDimensionsInPixels() {
        return new Vector2(tileWidth * levelWidth, tileHeight * levelHeight);
    }

    public Vector2 getDimensionsInTiles() {
        return new Vector2(levelWidth, levelHeight);
    }
}
