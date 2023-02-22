package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public abstract class Screens extends InputAdapter implements Screen {
    public static final float SCREEN_WIDTH = 800;
    public static final float SCREEN_HEIGHT = 480;

    public static final float WORLD_WIDTH = 8f;
    public static final float WORLD_HEIGHT = 4.8f;

    public MainGame game;

    public OrthographicCamera oCamUI;
    public OrthographicCamera oCamBox2D;
    public SpriteBatch spriteBatch;
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
}