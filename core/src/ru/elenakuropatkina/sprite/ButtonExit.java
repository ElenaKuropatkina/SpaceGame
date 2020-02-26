package ru.elenakuropatkina.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.elenakuropatkina.base.ScaledButton;
import ru.elenakuropatkina.math.Rect;

public class ButtonExit extends ScaledButton {

    private static final float PADDING = 0.06f;

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("exit"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.21f);
        setRight(worldBounds.getRight() - PADDING);
        setBottom(worldBounds.getBottom() + PADDING);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }
}
