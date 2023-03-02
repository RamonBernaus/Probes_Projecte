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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Assets;
import com.mygdx.game.DeadScreen;
import com.mygdx.game.Goblins.AssetsGoblins;
import com.mygdx.game.Goblins.Goblins;
import com.mygdx.game.MainGame;
import com.mygdx.game.Screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen extends Screens {
    Texture goblinTexture = new Texture("GoblinAtacFront-4.png");

    World oWorld;

    DeadScreen deadScreen;
    Guts guts;

    Array<Body> arrBodies;
    ArrayList<Goblins> goblinsList;

    Vector2 gutsPosition;

    float detectionRadius = 20f;

    Sprite keyframeGuts;
    Sprite keyframeGoblins;

    public GameScreen(MainGame game) {
        super(game);
        AssetsManager.load();
        AssetsGoblins.load();
        stage = new Stage();

        deadScreen = new DeadScreen();

        Vector2 gravity = new Vector2(0, 0);
        oWorld = new World(gravity, true);

        arrBodies = new Array<>();

        createGuts();
        createGoblins();


    }

    private void createGuts() {
        // Creem al guts
        guts = new Guts(4, 5, game);
        gutsPosition = guts.position;
        // Creem la "HitBox"
        BodyDef bd = new BodyDef();
        bd.position.x = guts.position.x;
        bd.position.y = guts.position.y;
        bd.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Guts.Width, Guts.Height);

        Body oBody = oWorld.createBody(bd);
        oBody.setUserData(guts);

        // Tanquem el shape
        shape.dispose();
    }

    public void createGoblins(){
        // Creem l'objecte random
        Random r = new Random();
        float x ,y;
        x = r.nextFloat();
        y = r.nextFloat();

        // Creem la "HitBox"
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Goblins.Width, Goblins.Height);

        Body oBody = oWorld.createBody(bd);
        oBody.setUserData(goblinsList);

        // Comencem amb 3 asteroides
        int numerogoblins = 1;

        // Creem l'ArrayList
        goblinsList = new ArrayList<>();

        // Afegim el primer asteroide a l'array i al grup
        Goblins goblin = new Goblins(x,y);
        goblinsList.add(goblin);
        //addActor(asteroid);

        // Des del segon fins l'últim asteroide
        for (int i = 1; i < numerogoblins; i++) {
        // Afegim l'asteroide
            goblin = new Goblins(x,y);
        // Afegim l'asteroide a l'ArrayList
            goblinsList.add(goblin);
        // Afegim l'asteroide al grup d'actors
            stage.addActor(goblin);
        }

        // Tanquem el shape
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
        }

        shapeRenderer.rect(10, 750, 200 * hpRatio, 20); // Dibuja el rectángulo de la barra de HP

        SpriteBatch batch;
        batch = new SpriteBatch();

        batch.begin();
        drawGoblins(batch);


        // Actualiza la posición de cada Goblin
        for (Goblins goblin: goblinsList) {
            // Calcula la distancia entre el Goblin y Guts
            float distance = gutsPosition.dst(goblin.position);

            // Si el Goblin está dentro del radio de detección, muévelo hacia Guts
            if (distance <= detectionRadius) {
                goblin.moveTowards(gutsPosition);
            }
        }
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
            for (Goblins goblin : goblinsList) {
            batch.draw(goblinTexture, goblin.position.x, goblin.position.y);
                System.out.println(goblin.position.x);
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
