package com.mygdx.game.Guts;

import static com.mygdx.game.Goblins.Goblins.GoblinsState.IDLE;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Assets;
import com.mygdx.game.Goblins.AssetsGoblins;
import com.mygdx.game.Goblins.Goblins;
import com.mygdx.game.MainGame;
import com.mygdx.game.Screens;

import java.util.ArrayList;
import java.util.List;

public class GameScreen extends Screens {

    World oWorld;

    Guts guts;

    Goblins goblin;

    Array<Body> arrBodies;

    List<Goblins> goblinsList;

    Box2DDebugRenderer renderer;

    Sprite keyframe;

    public GameScreen(MainGame game) {
        super(game);
        AssetsManager.load();

        Vector2 gravity = new Vector2(0, 0);
        oWorld = new World(gravity, true);

        arrBodies = new Array<>();

        this.goblinsList = new ArrayList<Goblins>();

        renderer = new Box2DDebugRenderer();

        createFloor();
        createGuts();
        createGoblins();
    }

    private void createFloor() {
        BodyDef bd = new BodyDef();
        bd.position.set(0, .6f);
        bd.type = BodyDef.BodyType.StaticBody;

        EdgeShape shape = new EdgeShape();
        shape.set(0, 0, WORLD_WIDTH, 0);

        FixtureDef fixDef = new FixtureDef();
        fixDef.shape = shape;
        fixDef.friction = 1f;

        Body oBody = oWorld.createBody(bd);
        oBody.createFixture(fixDef);
        shape.dispose();
    }

    private void createGuts() {
        guts = new Guts(4, 5);
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

    private void createGoblins() {
        goblin = new Goblins(4, 5);
        BodyDef bd = new BodyDef();
        bd.position.x = goblin.position.x;
        bd.position.y = goblin.position.y;
        bd.type = BodyDef.BodyType.DynamicBody;

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(Goblins.Width, Goblins.Height);


        Body oBody = oWorld.createBody(bd);
        oBody.setUserData(goblin);
        shape.dispose();
    }

    @Override
    public void update(float delta) {
        float accelX = 0, accelY = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT))
            accelX = -1;
        else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT))
            accelX = 1;

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN))
            accelY = -1;

        else if (Gdx.input.isKeyPressed(Input.Keys.UP))
            accelY = 1;


        oWorld.step(delta, 8, 6);
        oWorld.getBodies(arrBodies);

        for (Body body : arrBodies) {
            if (body.getUserData() instanceof Guts) {
                Guts obj = (Guts) body.getUserData();
                obj.update(body, delta, accelX, accelY);
            }
        }

        for (Goblins goblins : goblinsList) {
            switch (goblins.getState()) {
                case IDLE:
                    // Si el personaje está dentro del rango de detección del enemigo, cambia al estado de persecución
                    if (goblins.position.dst(guts.position) < 50) {
                        goblins.setState(Goblins.GoblinsState.CHASE);
                    }
                    break;
                case CHASE:
                    // Mueve al enemigo en dirección al personaje
                    Vector2 direction = new Vector2(guts.position).sub(goblins.position).nor();
                    goblins.position.add(direction.scl(goblins.velocity));
                    // Si el enemigo está lo suficientemente cerca del personaje, cambia al estado de ataque
                    if (goblins.position.dst(guts.position) < 10) {
                        goblins.setState(Goblins.GoblinsState.ATTACK);
                    }
                    break;
                case ATTACK:
                    // Ataca al personaje
                    //guts.takeDamage(10);
                    break;
            }
        }
    }

    @Override
    public void draw(float delta) {
        oCamUI.update();
        spriteBatch.setProjectionMatrix(oCamUI.combined);

        Viewport viewport = new FitViewport(640, 480); // crea un viewport con dimensiones iniciales
        Assets.backgroundSprite.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());


        spriteBatch.begin();
        Assets.font.draw(spriteBatch, "fps:" + Gdx.graphics.getFramesPerSecond(), 0, 20);
        spriteBatch.end();

        oCamBox2D.update();

        spriteBatch.setProjectionMatrix(oCamBox2D.combined);
        spriteBatch.begin();

        drawGuts();

        spriteBatch.end();
        renderer.render(oWorld, oCamBox2D.combined);
    }

    private void drawGuts() {
        keyframe = AssetsManager.straight;

        if (guts.Front) {
            keyframe = AssetsManager.walkFront.getKeyFrame(guts.stateTime, true);
        } else if (guts.Back) {
            keyframe = AssetsManager.walkBack.getKeyFrame(guts.stateTime, true);
        } else if (guts.Right) {
            keyframe = AssetsManager.walkRight.getKeyFrame(guts.stateTime, true);
        } else if (guts.Left) {
            keyframe = AssetsManager.walkLeft.getKeyFrame(guts.stateTime, true);
        }

        if (guts.velocity.x < 0) {
            keyframe.setPosition(guts.position.x + Guts.Draw_Width/2, guts.position.y - Guts.Draw_Height/2 + .25f);
            keyframe.setSize(-Guts.Draw_Width, Guts.Draw_Height);
        } else {
            keyframe.setPosition(guts.position.x - Guts.Draw_Width/2, guts.position.y - Guts.Draw_Height/2 + .25f);
            keyframe.setSize(Guts.Draw_Width, Guts.Draw_Height);

        }

        keyframe.draw(spriteBatch);
    }

    private void drawGoblins() {
        keyframe = AssetsGoblins.straight;
    }


    @Override
    public void dispose() {
        AssetsManager.dispose();
        oWorld.dispose();
        super.dispose();
    }
}
