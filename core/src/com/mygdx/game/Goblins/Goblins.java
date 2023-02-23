package com.mygdx.game.Goblins;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Goblins {
    public static final float Width = 64;
    public static final float Height = 64;


    public static final float Draw_Width = 1.3f;
    public static final float Draw_Height = 1.7f;

    static final float Walk_Frame_Duration = 0.25f;

    static final float WALK_SPEED = 3;

    public boolean Right;
    public boolean Left;
    public boolean Front;
    public boolean Back;

    public float stateTime = 0;
    private GoblinsState state;
    public Vector2 position;
    public Vector2 velocity;

    public Goblins(float x, float y) {
        position = new Vector2(x, y);
    }
    public void update(Body body, float delta, float accelX, float accelY) {
        position.x = body.getPosition().x;
        position.y = body.getPosition().y;

        velocity = body.getLinearVelocity();

        /*if (accelX == -1) {
            velocity.x = -WALK_SPEED;
            Left = !Right && !Front && !Back;
        } else if (accelX == 1) {
            velocity.x = WALK_SPEED;
            Right = !Left && !Front && !Back;
        } else {
            velocity.x = 0;
        }

        if (accelY == -1) {
            velocity.y = -WALK_SPEED;
            Front = !Left && !Right && !Back;
        } else if (accelY == 1) {
            velocity.y = WALK_SPEED;
            Back = !Left && !Front && !Back;
        } else {
            velocity.y = 0;
        }*/

        body.setLinearVelocity(velocity);
        stateTime += delta;
    }




    public GoblinsState getState() {
        return state;
    }

    public void setState(GoblinsState state) {
        this.state = state;
    }

    public enum GoblinsState {
        IDLE, // Enemigo inactivo
        CHASE, // Enemigo persiguiendo al personaje
        ATTACK // Enemigo atacando al personaje
    }
}
