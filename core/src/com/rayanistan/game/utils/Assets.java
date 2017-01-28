package com.rayanistan.game.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.utils.Array;

import static com.rayanistan.game.utils.Assets.Atlases.NPC_ATLAS;
import static com.rayanistan.game.utils.Assets.Atlases.PLAYER_ATLAS;
import static com.rayanistan.game.utils.Assets.Maps.MAP_1;

public class Assets {

    public static AssetManager am;

    private static String[] _maps = {MAP_1};
    private static String[] _atlases = {PLAYER_ATLAS, Atlases.NPC_ATLAS};

    class Atlases {
        static final String PLAYER_ATLAS = "sprites/player.atlas";
        static final String NPC_ATLAS = "sprites/npc.atlas";
    }

    class Maps {
        static final String MAP_1 = "maps/stage1.tmx";
    }


    public static void loadAll(AssetManager am) {
        Assets.am = am;

        for (String atlas : _atlases) {
            am.load(atlas, TextureAtlas.class);
        }

        am.setLoader(TiledMap.class, new TmxMapLoader());
        for (String map : _maps) {
            am.load(map, TiledMap.class);
        }
    }

    private static Array<TextureAtlas.AtlasRegion> getPlayerWalkingArray() {
        return am.get(PLAYER_ATLAS, TextureAtlas.class).findRegions("walk");
    }

    private static Array<TextureAtlas.AtlasRegion> getPlayerIdlingArray() {
        return am.get(PLAYER_ATLAS, TextureAtlas.class).findRegions("nothing_idle");
    }

    private static Array<TextureAtlas.AtlasRegion> getPlayerJumpingArray() {
        return am.get(PLAYER_ATLAS, TextureAtlas.class).findRegions("jump");
    }

    private static Array<TextureAtlas.AtlasRegion> getWizardIdlingArray() {
        return am.get(NPC_ATLAS, TextureAtlas.class).findRegions("w");
    }
    private static Array<TextureAtlas.AtlasRegion> getGunsmithIdlingArray(){
        return am.get(NPC_ATLAS, TextureAtlas.class).findRegions("g");
    }

    public static Animation getPlayerWalking() {
        return new Animation(1f / 16f, getPlayerWalkingArray(), Animation.PlayMode.LOOP);
    }

    public static Animation getPlayerIdling() {
        return new Animation(3f / 4f, getPlayerIdlingArray(), Animation.PlayMode.LOOP);
    }

    public static Animation getPlayerJumping() {
        return new Animation(1 / 11f, getPlayerJumpingArray(), Animation.PlayMode.NORMAL);
    }

    public static Animation getWizardIdling() {
        return new Animation(3f/4f, getWizardIdlingArray(), Animation.PlayMode.LOOP);
    }
    public static Animation getGunsmithIdling(){
        return new Animation (3f/4f, getGunsmithIdlingArray(), Animation.PlayMode.LOOP);
    }

    public static TiledMap getMap1() {
        return am.get(MAP_1, TiledMap.class);
    }
}
