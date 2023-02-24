package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.mygdx.game.Guts.GameScreen;
import com.mygdx.game.Guts.Guts;

public abstract class Screens extends InputAdapter implements Screen {
    public static final float SCREEN_WIDTH = 1600;
    public static final float SCREEN_HEIGHT = 800;

    public static final float WORLD_WIDTH = 8f;
    public static final float WORLD_HEIGHT = 4.8f;

    public MainGame game;
    public Guts guts;

    public OrthographicCamera oCamUI;
    public OrthographicCamera oCamBox2D;
    public static SpriteBatch spriteBatch;
    public Stage stage;

    public Screens(MainGame game) {
        this.game = game;

        // We will add UI elements to the stage
        stage = new Stage(new StretchViewport(Screens.SCREEN_WIDTH, Screens.SCREEN_HEIGHT));

        // Create the UI Camera and center it on the screen
        oCamUI = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        oCamUI.position.set(SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f, 0);

        // Create the Game Camera and center it on the screen
        oCamBox2D = new OrthographicCamera(WORLD_WIDTH, WORLD_HEIGHT);
        oCamBox2D.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);

        // We need it to tell the InputAdapter and stage when we receive events
        InputMultiplexer input = new InputMultiplexer(this, stage);
        Gdx.input.setInputProcessor(input);

        spriteBatch = new SpriteBatch();
    }

    // This functions will be called automatically 60 times per second (60 FPS)
    @Override
    public void render(float delta) {

        // Update all the physics of the game
        update(delta);

        // Update the stage (mostly UI elements)
        stage.act(delta);

        // Clear everything on the screen
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Borra la pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Dibuja el inicio de panatalla
        spriteBatch.begin();
        Assets.backgroundSprite.draw(spriteBatch);
        spriteBatch.end();

        // Draw the game elements on the screen
        draw(delta);

        //Creem la barar de vida
        ShapeRenderer shapeRenderer;
        shapeRenderer = new ShapeRenderer();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.GREEN);
        float hpRatio = (float)guts.getHP() / 100f; // Calcula la proporción de HP restante
        if(hpRatio < 50){
            shapeRenderer.setColor(Color.ORANGE);
        } else if (hpRatio < 25){
            shapeRenderer.setColor(Color.RED);
        }
        shapeRenderer.rect(10, 10, 200 * hpRatio, 20); // Dibuja el rectángulo de la barra de HP
        shapeRenderer.end();

        // Spawnear un Goblin cada 2 segundos en una posición aleatoria
        /*if (TimeUtils.nanoTime() % 2000000000 == 0) {
            float x = MathUtils.random(0, Gdx.graphics.getWidth());
            float y = MathUtils.random(0, Gdx.graphics.getHeight());
            GameScreen.spawnGoblin(x, y);
        }*/

        // Draw the stage element on the screen
        stage.draw();
    }

    public abstract void draw(float delta);

    public abstract void update(float delta);

    @Override
    public void resize(int width, int height) {
        // If the screen size change we adjust the stage
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE || keycode == Input.Keys.BACK)
            if (this instanceof MainScreens) {
                Gdx.app.exit();
            } else {
                game.setScreen(new MainScreens(game));
            }
        return super.keyDown(keycode);
    }
    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.ENTER || keycode == Input.Keys.SPACE)
                game.setScreen(new MainScreens(game));
        return super.keyDown(keycode);
    }
}