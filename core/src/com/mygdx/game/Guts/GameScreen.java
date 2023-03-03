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
import com.mygdx.game.MyContactListener;
import com.mygdx.game.Screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen extends Screens {
    Texture goblinTexture = new Texture("GoblinAtacFront-4.png");

    World oWorld;

    DeadScreen deadScreen;

    Guts guts;
    Goblins goblin_body;
    Body GoblinBody, GutsBody, BossBody;

    Array<Body> arrBodies;
    ArrayList<Goblins> goblinsList;

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

        //oWorld.setBodyCapacity(500);

        arrBodies = new Array<>();

        createGuts();
        createGoblins();


    }

    private void createGuts() {
        // Creem al guts
        guts = new Guts(4, 1.5f, game);

        // Creem la "HitBox"
        BodyDef bd = new BodyDef();
        bd.position.x = guts.position.x;
        bd.position.y = guts.position.y;
        bd.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Guts.Width, Guts.Height);

        GutsBody = oWorld.createBody(bd);
        GutsBody.setUserData(guts);
        MyContactListener.cuerpoGuts = GutsBody;

        // Tanquem el shape
        shape.dispose();
    }

    public void createGoblins() {
        // Creem la posicio random
        Random r = new Random();
        float x, y;
        x = r.nextFloat();
        y = r.nextFloat();

        goblin_body = new Goblins(x, y);

        // Creem la "HitBox"
        BodyDef bd = new BodyDef();
        bd.position.x = goblin_body.position.x;
        bd.position.y = goblin_body.position.y;
        bd.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Goblins.Width, Goblins.Height);

        GoblinBody = oWorld.createBody(bd);
        GoblinBody.setUserData(goblin_body);
        MyContactListener.cuerpoGoblin = GoblinBody;

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

        float hpRatio = (float) guts.getHP() / 100f; // Calcula la proporción de HP restante
        if (hpRatio <= 0.5f && hpRatio >= 0.25f) {
            shapeRenderer.setColor(Color.ORANGE);
        } else if (hpRatio <= 0.25f) {
            shapeRenderer.setColor(Color.RED);
        }

        shapeRenderer.rect(10, 750, 200 * hpRatio, 20); // Dibuja el rectángulo de la barra de HP

        GoblinAtac();
        GutsAtac();

        shapeRenderer.end();

    }

    @Override
    public void update(float delta) {
        float accelXGuts = 0, accelYGuts = 0;
        float accelXGoblins = 0, accelYGoblins = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            accelXGuts = -1;
            guts.Left = false;
            guts.Right = true;
            guts.Front = false;
            guts.Back = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            accelXGuts = 1;
            guts.Right = false;
            guts.Left = true;
            guts.Front = false;
            guts.Back = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            accelYGuts = -1;
            guts.Left = false;
            guts.Right = false;
            guts.Front = true;
            guts.Back = false;
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            accelYGuts = 1;
            guts.Left = false;
            guts.Right = false;
            guts.Front = false;
            guts.Back = true;
        } else {
            guts.Left = false;
            guts.Right = false;
            guts.Front = false;
            guts.Back = false;
        }


        if (goblin_body.position.x > 1) {
            accelXGoblins = 1;
            goblin_body.Left = false;
            goblin_body.Right = true;
            goblin_body.Front = false;
            goblin_body.Back = false;
        } else if (goblin_body.position.x < 1) {
            accelXGoblins = -1;
            goblin_body.Left = false;
            goblin_body.Right = true;
            goblin_body.Front = false;
            goblin_body.Back = false;
        } else {
            goblin_body.Left = false;
            goblin_body.Right = false;
            goblin_body.Front = false;
            goblin_body.Back = false;
        }

        if (goblin_body.position.y > 1) {
            accelYGoblins = 1;
            goblin_body.Left = false;
            goblin_body.Right = false;
            goblin_body.Front = false;
            goblin_body.Back = true;
        } else if (goblin_body.position.y < 1) {
            accelYGoblins = -1;
            goblin_body.Left = false;
            goblin_body.Right = false;
            goblin_body.Front = true;
            goblin_body.Back = false;
        } else {
            goblin_body.Left = false;
            goblin_body.Right = false;
            goblin_body.Front = false;
            goblin_body.Back = false;
        }

        oWorld.step(delta, 8, 6);
        oWorld.getBodies(arrBodies);

        for (Body body : arrBodies) {
            if (body.getUserData() instanceof Guts) {
                Guts obj = (Guts) body.getUserData();
                obj.update(body, delta, accelXGuts, accelYGuts);
            }
        }

        for (Body body : arrBodies) {
            if (body.getUserData() instanceof Goblins) {
                Goblins obj = (Goblins) body.getUserData();
                obj.update(body, delta, guts, 1, accelXGoblins, accelYGoblins);
            }
        }
        for (Body body : arrBodies) {
            if (body.getUserData() instanceof Goblins) {
                Goblins obj = (Goblins) body.getUserData();
                obj.update(body, delta, guts, 1, accelXGoblins, accelYGoblins);
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
        drawGoblins();

        spriteBatch.end();
    }

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
            keyframeGuts.setPosition(guts.position.x + Guts.Draw_Width / 2, guts.position.y - Guts.Draw_Height / 2 + .25f);
            keyframeGuts.setSize(-Guts.Draw_Width, Guts.Draw_Height);
        } else {
            keyframeGuts.setPosition(guts.position.x - Guts.Draw_Width / 2, guts.position.y - Guts.Draw_Height / 2 + .25f);
            keyframeGuts.setSize(Guts.Draw_Width, Guts.Draw_Height);

        }

        keyframeGuts.draw(spriteBatch);
    }

    private void drawGoblins() {
        keyframeGoblins = AssetsGoblins.straight;

        if (goblin_body.Front) {
            keyframeGoblins = AssetsGoblins.walkFront.getKeyFrame(goblin_body.stateTime, true);
        } else if (goblin_body.Back) {
            keyframeGoblins = AssetsGoblins.walkBack.getKeyFrame(goblin_body.stateTime, true);
        } else if (goblin_body.Right) {
            keyframeGoblins = AssetsGoblins.walkRight.getKeyFrame(goblin_body.stateTime, true);
        } else if (goblin_body.Left) {
            keyframeGoblins = AssetsGoblins.walkLeft.getKeyFrame(goblin_body.stateTime, true);
        }

        if (goblin_body.velocity.x <= 0 || goblin_body.velocity.y <= 0) {
            keyframeGoblins.setPosition(goblin_body.position.x + Goblins.Draw_Width / 2, goblin_body.position.y - Goblins.Draw_Height / 2 + .25f);
            keyframeGoblins.setSize(-Goblins.Draw_Width, Goblins.Draw_Height);
        } else {
            keyframeGoblins.setPosition(goblin_body.position.x - Goblins.Draw_Width / 2, goblin_body.position.y - Goblins.Draw_Height / 2 + .25f);
            keyframeGoblins.setSize(Goblins.Draw_Width, Goblins.Draw_Height);
        }

        keyframeGoblins.draw(spriteBatch);

    }

    public void GoblinAtac(){
        int GoblinX = Math.round(goblin_body.position.x);
        int GutsX = Math.round(guts.position.x);
        int GoblinY = Math.round(goblin_body.position.y);
        int GutsY = Math.round(guts.position.y);

        if(GutsX - GoblinX < 0.0025 && GutsY - GoblinY < 0.0025){
            Guts.reduceHP(1);
        }
    }

    public void GutsAtac(){
        int GoblinX = Math.round(goblin_body.position.x);
        int GutsX = Math.round(guts.position.x);
        int GoblinY = Math.round(goblin_body.position.y);
        int GutsY = Math.round(guts.position.y);

        if(GutsX - GoblinX < 0.5 && GutsY - GoblinY < 0.5 && Gdx.input.isKeyPressed(Input.Keys.C) ){
            Goblins.reduceHP(5);
            if(Goblins.Dead){
                oWorld.destroyBody(GoblinBody);
            }
        }
    }

    @Override
    public void dispose() {
        AssetsManager.dispose();
        AssetsGoblins.dispose();
        oWorld.dispose();
        super.dispose();
    }
}
