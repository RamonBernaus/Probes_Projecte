package com.mygdx.game.Guts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetsManager {
    static Sprite straight;

    static Animation<Sprite> walkFront, walkBack, walkRight, walkLeft;

    static TextureAtlas atlas;



    public static void load() {
        atlas = new TextureAtlas(Gdx.files.internal("Guts.txt"));

        straight = atlas.createSprite("Guts-Sprites-Straight");

        walkBack = new Animation<>(
                Guts.Walk_Frame_Duration,
                atlas.createSprite("Guts-Sprites-Back-1"),
                atlas.createSprite("Guts-Sprites-Back-2"),
                atlas.createSprite("Guts-Sprites-Back-3"),
                atlas.createSprite("Guts-Sprites-Back-4"));

        walkFront = new Animation<>(
                Guts.Walk_Frame_Duration,
                atlas.createSprite("Guts-Sprites-Front-1"),
                atlas.createSprite("Guts-Sprites-Front-2"),
                atlas.createSprite("Guts-Sprites-Front-3"),
                atlas.createSprite("Guts-Sprites-Front-4"));

        walkRight = new Animation<>(
                Guts.Walk_Frame_Duration,
                atlas.createSprite("Guts-Sprites-Right-1"),
                atlas.createSprite("Guts-Sprites-Right-2"),
                atlas.createSprite("Guts-Sprites-Right-3"),
                atlas.createSprite("Guts-Sprites-Right-4"));

        walkLeft = new Animation<>(
                Guts.Walk_Frame_Duration,
                atlas.createSprite("Guts-Sprites-Left-1"),
                atlas.createSprite("Guts-Sprites-Left-2"),
                atlas.createSprite("Guts-Sprites-Left-3"),
                atlas.createSprite("Guts-Sprites-Left-4"));
    }
    public static void dispose(){
        atlas.dispose();
    }
}

