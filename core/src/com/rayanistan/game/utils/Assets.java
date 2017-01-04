package com.rayanistan.game.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Assets {

    private static AssetManager am;
    private static String[] _maps = {Maps.MAP_1};
    private static String[] _atlases = {Atlases.PLAYER_ATLAS, Atlases.NPC_ATLAS};

    public class Atlases {
        public static final String PLAYER_ATLAS = "sprites/player.atlas";
        public static final String NPC_ATLAS = "sprites/npc.atlas";
    }

    public class Maps {
        public static final String MAP_1 = "maps/stage1.tmx";
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

    public static Object getAsset(String object, Class<?> type) {
        return am.get(object);
    }
}
