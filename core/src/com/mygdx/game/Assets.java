package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class Assets {
    public static BitmapFont font;

    public static TextButton.TextButtonStyle txButtonStyle;
    public static Label.LabelStyle labelStyle;
    public static ScrollPane.ScrollPaneStyle scrollPaneStyle;
    public static Texture backgroundTexture = new Texture("BackgroundMenu.png");
    public static Sprite backgroundSprite = new Sprite(backgroundTexture);

    public static void load() {
        font = new BitmapFont();

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("ui.txt"));

        txButtonStyle = new TextButton.TextButtonStyle(new NinePatchDrawable(
                atlas.createPatch("bt")), new NinePatchDrawable(
                atlas.createPatch("btDown")), null, font);

        labelStyle = new Label.LabelStyle(Assets.font, Color.GREEN);

        NinePatchDrawable knob = new NinePatchDrawable(
                atlas.createPatch("scroll"));
        scrollPaneStyle = new ScrollPane.ScrollPaneStyle(null, knob, knob, knob, knob);
    }
}
