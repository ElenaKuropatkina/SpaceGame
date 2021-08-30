package ru.elenakuropatkina;

import com.badlogic.gdx.Game;

import ru.elenakuropatkina.screen.MenuScreen;

public class StarGame extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}

