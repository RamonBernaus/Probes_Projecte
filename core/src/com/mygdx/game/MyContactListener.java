package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.Guts.GameScreen;
import com.mygdx.game.Guts.Guts;


public class MyContactListener implements ContactListener {

    public MyContactListener() {
    }

    public static Body cuerpoGuts;
    public static Body cuerpoGoblin;

    @Override
    public void beginContact(Contact contact) {
        // Obtenemos los cuerpos que han colisionado
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        // Verificamos si el cuerpo X y el cuerpo Y están en contacto

        if (bodyA == cuerpoGuts && bodyB == cuerpoGoblin || bodyA == cuerpoGoblin && bodyB == cuerpoGuts) {
            // Calculamos la distancia entre los cuerpos
            float distance = cuerpoGuts.getPosition().dst(cuerpoGoblin.getPosition());

            System.out.println(cuerpoGuts.getPosition());

            // Verificamos si la distancia es menor que un valor determinado (por ejemplo, 1 unidad)
            if (distance < 20f) {
                // Ejecutamos la función que deseamos
                Guts.reduceHP(10);
            }
        }
    }




    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    // Otros métodos de ContactListener
}
