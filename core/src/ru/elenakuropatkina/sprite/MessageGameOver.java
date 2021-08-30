package ru.elenakuropatkina.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.elenakuropatkina.base.Sprite;
import ru.elenakuropatkina.math.Rect;


public class MessageGameOver extends Sprite {

    public MessageGameOver(TextureAtlas atlas) {
        super(atlas.findRegion("gameOver"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.08f);
        setTop(0.1f);
    }
}
