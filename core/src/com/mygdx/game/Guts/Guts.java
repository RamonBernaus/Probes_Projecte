package com.mygdx.game.Guts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.MainGame;
import com.mygdx.game.MainScreens;
import com.mygdx.game.Screens;


public class Guts {
    public MainGame game;

    static final float Width = 52;
    static final float Height = 52;

    static final float Draw_Width = 1.3f;
    static final float Draw_Height = 1.7f;

    static final float Walk_Frame_Duration = 0.25f;

    static final float WALK_SPEED = 3;
    private int hp;
    private ShapeRenderer shapeRenderer;
    boolean Right;
    boolean Left;
    boolean Front;
    boolean Back;

    float stateTime = 0;

    public Vector2 position;
    Vector2 velocity;



    public Guts(float x, float y, MainGame game) {
        position = new Vector2(x, y);
        this.game = game;
        hp = 100;
        shapeRenderer = new ShapeRenderer();

    }

    public void update(Body body, float delta, float accelX, float accelY) {
        position.x = body.getPosition().x;
        position.y = body.getPosition().y;

        velocity = body.getLinearVelocity();


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

        body.setLinearVelocity(velocity);
        stateTime += delta;
    }

    // Método para reducir HP
    public void reduceHP(int damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0; // Asegura que HP no sea negativo
            game.setScreen(new MainScreens(game));
        }
    }

    // Método para aumentar HP
    public void increaseHP(int heal) {
        hp += heal;
        if (hp > 100) {
            hp = 100; // Asegura que HP no sea mayor que 100
        }
    }

    // Método para obtener el valor de HP
    public int getHP() {
        return hp;
    }

}
