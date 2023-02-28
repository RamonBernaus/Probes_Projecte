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

    public float stateTime = 0;
    private GoblinsState state;
    public Vector2 position;
    public Vector2 velocity;

    public Goblins(float x, float y) {
        position = new Vector2(x, y);
    }

    public void moveTowards(Vector2 target) {
        // Calcula la dirección hacia el objetivo
        Vector2 direction = target.sub(position).nor();

        // Mueve al Goblin hacia el objetivo con una velocidad fija
        float speed = 2f;
        position.add(direction.scl(speed));
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
