package com.mygdx.game.Guts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;




public class Guts {
    static final float Width = 52;
    static final float Height = 52;

    static final float Draw_Width = 1.3f;
    static final float Draw_Height = 1.7f;

    static final float Walk_Frame_Duration = 0.25f;

    static final float WALK_SPEED = 3;

    boolean Right;
    boolean Left;
    boolean Front;
    boolean Back;

    float stateTime = 0;

    Vector2 position;
    Vector2 velocity;



    public Guts(float x, float y) {
        position = new Vector2(x, y);
    }

    public void update(Body body, float delta, float accelX, float accelY) {
        position.x = body.getPosition().x;
        position.y = body.getPosition().y;

        velocity = body.getLinearVelocity();


        if (accelX == -1) {
            velocity.x = -WALK_SPEED;
            //Left = !Right && !Front && !Back;
        } else if (accelX == 1) {
            velocity.x = WALK_SPEED;
            //Right = !Left && !Front && !Back;
        } else {
            velocity.x = 0;

        }

        if (accelY == -1) {
            velocity.y = -WALK_SPEED;
            //Front = !Left && !Right && !Back;
        } else if (accelY == 1) {
            velocity.y = WALK_SPEED;
            //Back = !Left && !Right && !Front;
        } else {
            velocity.y = 0;
        }

        body.setLinearVelocity(velocity);
        stateTime += delta;
    }

}
