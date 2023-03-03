package com.mygdx.game.Goblins;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.DeadScreen;
import com.mygdx.game.Guts.Guts;
import com.mygdx.game.MainScreens;

public class Goblins extends Actor {
    public static final float Width = 64;
    public static final float Height = 64;


    public static final float Draw_Width = 1.3f;
    public static final float Draw_Height = 1.7f;

    static final float Walk_Frame_Duration = 0.25f;

    static final float WALK_SPEED = 1;

    private static int hp;
    public static boolean Dead;

    public boolean Right;
    public boolean Left;
    public boolean Front;
    public boolean Back;

    public float stateTime = 0;

    public Vector2 position;
    public Vector2 velocity;
    public Vector2 targetPosition;
    public Vector2 currentPosition;
    public Vector2 direction;
    public Vector2 guts_position;

    public Goblins(float x, float y) {
        position = new Vector2(x, y);
        hp = 10;
    }



    public void update(Body body, float delta, Guts guts, float speed, float accelX, float accelY) {
        position.x = body.getPosition().x;
        position.y = body.getPosition().y;

        velocity = body.getLinearVelocity();

        currentPosition = new Vector2(position.x, position.y);
        targetPosition = new Vector2(guts.position.x, guts.position.y);

        direction = targetPosition.cpy().sub(currentPosition).nor();

        // Calculamos la velocidad actual del cuerpo
        guts_position = direction.scl(speed);

        body.setLinearVelocity(guts_position);

        if (accelX == -1) {
            velocity.x = -WALK_SPEED;
        } else if (accelX == 1) {
            velocity.x = WALK_SPEED;
        } else {
            velocity.x = 0;
        }

        if (accelY == -1) {
            velocity.y = -WALK_SPEED;
        } else if (accelY == 1) {
            velocity.y = WALK_SPEED;
        } else {
            velocity.y = 0;
        }

        stateTime += delta;
    }

    public static void reduceHP(int damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0; // Asegura que HP no sea negativo
            Dead = true;
        }
    }
}
