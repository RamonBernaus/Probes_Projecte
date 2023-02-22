package com.mygdx.game;

import com.badlogic.gdx.Game;

public class MainGame extends Game {

    @Override
    public void create() {
        Assets.load();
        setScreen(new MainScreens(this));
    }
}
