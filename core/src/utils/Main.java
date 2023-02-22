package utils;

import com.mygdx.game.Guts.GameScreen;
import com.mygdx.game.Screens;

public enum Main {
    GAME("Iniciar el juego", GameScreen.class),
    GAME2("Acabar joc", GameScreen.class);

    public final String name;
    public final Class<? extends Screens> clazz;

    Main(String label, Class<? extends Screens> clazz) {
        this.name = label;
        this.clazz = clazz;
    }


}
