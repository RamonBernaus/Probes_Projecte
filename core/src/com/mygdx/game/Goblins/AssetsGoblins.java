package com.mygdx.game.Goblins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetsGoblins {
    public static Sprite straight;

    public static Animation<Sprite> walkFront;
    public static Animation<Sprite> walkBack;
    public static Animation<Sprite> walkRight;
    public static Animation<Sprite> walkLeft;

    static TextureAtlas atlas;



    public static void load() {
        atlas = new TextureAtlas(Gdx.files.internal("Goblins.txt"));

        straight = atlas.createSprite("GoblinAtacBack-1");

        walkBack = new Animation<>(
                Goblins.Walk_Frame_Duration,
                atlas.createSprite("GoblinAtacBack-1"),
                atlas.createSprite("GoblinAtacBack-2"),
                atlas.createSprite("GoblinAtacBack-3"),
                atlas.createSprite("GoblinAtacBack-4"),
                atlas.createSprite("GoblinAtacBack-5"),
                atlas.createSprite("GoblinAtacBack-6"),
                atlas.createSprite("GoblinAtacBack-7"),
                atlas.createSprite("GoblinAtacBack-8"),
                atlas.createSprite("GoblinAtacBack-9"),
                atlas.createSprite("GoblinAtacBack-10"),
                atlas.createSprite("GoblinAtacBack-11"));

        walkFront = new Animation<>(
                Goblins.Walk_Frame_Duration,
                atlas.createSprite("GoblinAtacFront-1"),
                atlas.createSprite("GoblinAtacFront-2"),
                atlas.createSprite("GoblinAtacFront-3"),
                atlas.createSprite("GoblinAtacFront-4"),
                atlas.createSprite("GoblinAtacFront-5"),
                atlas.createSprite("GoblinAtacFront-6"),
                atlas.createSprite("GoblinAtacFront-7"),
                atlas.createSprite("GoblinAtacFront-8"),
                atlas.createSprite("GoblinAtacFront-9"),
                atlas.createSprite("GoblinAtacFront-10"),
                atlas.createSprite("GoblinAtacFront-11"));

        walkRight = new Animation<>(
                Goblins.Walk_Frame_Duration,
                atlas.createSprite("GoblinAtacRigth-1"),
                atlas.createSprite("GoblinAtacRigth-2"),
                atlas.createSprite("GoblinAtacRigth-3"),
                atlas.createSprite("GoblinAtacRigth-4"),
                atlas.createSprite("GoblinAtacRigth-5"),
                atlas.createSprite("GoblinAtacRigth-6"),
                atlas.createSprite("GoblinAtacRigth-7"),
                atlas.createSprite("GoblinAtacRigth-8"),
                atlas.createSprite("GoblinAtacRigth-9"),
                atlas.createSprite("GoblinAtacRigth-10"),
                atlas.createSprite("GoblinAtacRigth-11"));

        walkLeft = new Animation<>(
                Goblins.Walk_Frame_Duration,
                atlas.createSprite("GoblinAtacLeft-1"),
                atlas.createSprite("GoblinAtacLeft-2"),
                atlas.createSprite("GoblinAtacLeft-3"),
                atlas.createSprite("GoblinAtacLeft-4"),
                atlas.createSprite("GoblinAtacLeft-5"),
                atlas.createSprite("GoblinAtacLeft-6"),
                atlas.createSprite("GoblinAtacLeft-7"),
                atlas.createSprite("GoblinAtacLeft-8"),
                atlas.createSprite("GoblinAtacLeft-9"),
                atlas.createSprite("GoblinAtacLeft-10"),
                atlas.createSprite("GoblinAtacLeft-11"));
    }
    public static void dispose(){
        atlas.dispose();
    }
}
