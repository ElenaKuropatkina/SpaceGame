package ru.elenakuropatkina.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.elenakuropatkina.base.ScaledButton;
import ru.elenakuropatkina.math.Rect;
import ru.elenakuropatkina.screen.GameScreen;


public class ButtonNewGame extends ScaledButton {

    private final Game game;

    public ButtonNewGame(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("newGame"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.06f);
        setTop(-0.05f);
    }

    @Override
    public void action() {
        System.out.println("NEW_GAME");
        game.setScreen(new GameScreen(game));
    }
}

