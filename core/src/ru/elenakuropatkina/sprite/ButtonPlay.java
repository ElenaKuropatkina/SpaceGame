package ru.elenakuropatkina.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.elenakuropatkina.base.ScaledButton;
import ru.elenakuropatkina.math.Rect;
import ru.elenakuropatkina.screen.GameScreen;


public class ButtonPlay extends ScaledButton {

    private static final float PADDING = 0.07f;

    private final Game game;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("play"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.18f);
        setLeft(worldBounds.getLeft() + PADDING);
        setBottom(worldBounds.getBottom() + PADDING);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen(game));
    }
}
