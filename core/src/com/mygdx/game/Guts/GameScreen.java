package com.mygdx.game.Guts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Assets;
import com.mygdx.game.DeadScreen;
import com.mygdx.game.Goblins.AssetsGoblins;
import com.mygdx.game.Goblins.Goblins;
import com.mygdx.game.MainGame;
import com.mygdx.game.Screens;

import java.util.List;

public class GameScreen extends Screens {
    Texture goblinTexture = new Texture("GoblinAtacFront-4.png");

    World oWorld;

    DeadScreen deadScreen;
    Guts guts;

    Array<Body> arrBodies;
    List<Goblins> goblin;

    Vector2 gutsPosition;

    float detectionRadius = 100f;

    Sprite keyframeGuts;

    public GameScreen(MainGame game) {
        super(game);
        AssetsManager.load();
        AssetsGoblins.load();

        deadScreen = new DeadScreen();

        Vector2 gravity = new Vector2(0, 0);
        oWorld = new World(gravity, true);

        arrBodies = new Array<>();

        createGuts();
    }

    private void createGuts() {
        guts = new Guts(4, 5, game);
        BodyDef bd = new BodyDef();
        bd.position.x = guts.position.x;
        bd.position.y = guts.position.y;
        bd.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Guts.Width, Guts.Height);


        Body oBody = oWorld.createBody(bd);
        oBody.setUserData(guts);

        shape.dispose();
    }


    @Override
    public void render(float delta) {
        super.render(delta);
        //Creem la barar de vida
        ShapeRenderer shapeRenderer;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        float hpRatio = (float)guts.getHP() / 100f; // Calcula la proporción de HP restante
        if(hpRatio <= 0.5f && hpRatio >= 0.25f){
            shapeRenderer.setColor(Color.ORANGE);
        } else if (hpRatio <= 0.25f){
            shapeRenderer.setColor(Color.RED);
        } else if (hpRatio <= 0){
            game.setScreen(deadScreen);
        }
        shapeRenderer.rect(10, 10, 200 * hpRatio, 20); // Dibuja el rectángulo de la barra de HP


        // Actualiza la posición de cada Goblin
        for (Goblins goblin: goblin) {
            // Calcula la distancia entre el Goblin y Guts
            float distance = gutsPosition.dst(goblin.position);

            // Si el Goblin está dentro del radio de detección, muévelo hacia Guts
            if (distance <= detectionRadius) {
                goblin.moveTowards(gutsPosition);
            }
        }

        SpriteBatch batch;
        batch = new SpriteBatch();
        batch.begin();
        drawGoblins(batch);
        batch.end();

        shapeRenderer.end();
    }

    @Override
    public void update(float delta) {
        float accelXGuts = 0, accelYGuts = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            accelXGuts = -1;
            guts.Left = false;
            guts.Right = true;
            guts.Front = false;
            guts.Back = false;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            accelXGuts = 1;
            guts.Right = false;
            guts.Left = true;
            guts.Front = false;
            guts.Back = false;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            accelYGuts = -1;
            guts.Left = false;
            guts.Right = false;
            guts.Front = true;
            guts.Back = false;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            accelYGuts = 1;
            guts.Left = false;
            guts.Right = false;
            guts.Front = false;
            guts.Back = true;
        }
        else {
            guts.Left = false;
            guts.Right = false;
            guts.Front = false;
            guts.Back = false;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.J)){
            guts.reduceHP(1);
        }

        oWorld.step(delta, 8, 6);
        oWorld.getBodies(arrBodies);

        for (Body body : arrBodies) {
            if (body.getUserData() instanceof Guts) {
                Guts obj = (Guts) body.getUserData();
                obj.update(body, delta, accelXGuts, accelYGuts);
            }
        }

/*        for (Goblins goblins : goblinsList) {
            switch (goblin.getState()) {
                case IDLE:
                    // Si el personaje está dentro del rango de detección del enemigo, cambia al estado de persecución
                    if (goblin.position.dst(guts.position) < 50) {
                        goblin.setState(Goblins.GoblinsState.CHASE);
                    }
                    break;
                case CHASE:
                    // Mueve al enemigo en dirección al personaje
                    Vector2 direction = new Vector2(guts.position).sub(goblin.position).nor();
                    goblin.position.add(direction.scl(goblin.velocity));
                    // Si el enemigo está lo suficientemente cerca del personaje, cambia al estado de ataque
                    if (goblin.position.dst(guts.position) < 10) {
                        goblin.setState(Goblins.GoblinsState.ATTACK);
                    }
                    break;
                case ATTACK:
                    // Ataca al personaje
                    //guts.takeDamage(10);
                    break;
            }*/
        }

    @Override
    public void draw(float delta) {
        oCamUI.update();
        spriteBatch.setProjectionMatrix(oCamUI.combined);

        Viewport viewport = new FitViewport(1600, 800); // crea un viewport con dimensiones iniciales
        Assets.backgroundSprite.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());


        spriteBatch.begin();
        Assets.font.draw(spriteBatch, "fps:" + Gdx.graphics.getFramesPerSecond(), 0, 20);
        spriteBatch.end();

        oCamBox2D.update();

        spriteBatch.setProjectionMatrix(oCamBox2D.combined);
        spriteBatch.begin();

        drawGuts();
        //drawGoblins();


        spriteBatch.end();
    }

    public void drawGoblins(SpriteBatch batch) {
        for (Goblins goblin : goblin) {
            batch.draw(goblinTexture, goblin.position.x, goblin.position.y);
        }
    }


    /*private void drawGoblins() {
        keyframeGoblins = AssetsGoblins.straight;

        if (goblin.Front) {
            keyframeGoblins = AssetsGoblins.walkFront.getKeyFrame(goblin.stateTime, true);
        } else if (goblin.Back) {
            keyframeGoblins = AssetsGoblins.walkBack.getKeyFrame(goblin.stateTime, true);
        } else if (goblin.Right) {
            keyframeGoblins = AssetsGoblins.walkRight.getKeyFrame(goblin.stateTime, true);
        } else if (goblin.Left) {
            keyframeGoblins = AssetsGoblins.walkLeft.getKeyFrame(goblin.stateTime, true);
        }

        if (goblin.velocity.x <= 0 || goblin.velocity.y <= 0) {
            keyframeGoblins.setPosition(goblin.position.x + Goblins.Draw_Width/2, goblin.position.y - Goblins.Draw_Height/2 + .25f);
            keyframeGoblins.setSize(-Goblins.Draw_Width, Goblins.Draw_Height);
        } else {
            keyframeGoblins.setPosition(goblin.position.x - Goblins.Draw_Width/2, goblin.position.y - Goblins.Draw_Height/2 + .25f);
            keyframeGoblins.setSize(Goblins.Draw_Width, Goblins.Draw_Height);
        }

        keyframeGoblins.draw(spriteBatch);
    }*/

    private void drawGuts() {
        keyframeGuts = AssetsManager.straight;

        if (guts.Front) {
            keyframeGuts = AssetsManager.walkFront.getKeyFrame(guts.stateTime, true);
        } else if (guts.Back) {
            keyframeGuts = AssetsManager.walkBack.getKeyFrame(guts.stateTime, true);
        } else if (guts.Right) {
            keyframeGuts = AssetsManager.walkRight.getKeyFrame(guts.stateTime, true);
        } else if (guts.Left) {
            keyframeGuts = AssetsManager.walkLeft.getKeyFrame(guts.stateTime, true);
        }

        if (guts.velocity.x <= 0 || guts.velocity.y <= 0) {
            keyframeGuts.setPosition(guts.position.x + Guts.Draw_Width/2, guts.position.y - Guts.Draw_Height/2 + .25f);
            keyframeGuts.setSize(-Guts.Draw_Width, Guts.Draw_Height);
        } else {
            keyframeGuts.setPosition(guts.position.x - Guts.Draw_Width/2, guts.position.y - Guts.Draw_Height/2 + .25f);
            keyframeGuts.setSize(Guts.Draw_Width, Guts.Draw_Height);

        }

        keyframeGuts.draw(spriteBatch);
    }

    @Override
    public void dispose() {
        AssetsManager.dispose();
        AssetsGoblins.dispose();
        oWorld.dispose();
        super.dispose();
    }
}
