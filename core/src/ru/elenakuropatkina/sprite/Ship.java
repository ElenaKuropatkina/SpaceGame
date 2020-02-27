package ru.elenakuropatkina.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.elenakuropatkina.base.Sprite;
import ru.elenakuropatkina.math.Rect;


public class Ship extends Sprite {

    private Vector2 v, touch, temp;
    private Rect worldBounds;
    public static final float V = 0.01f;


    public Ship(TextureAtlas atlas) {
        super(atlas.findRegion("ship"));
        v = new Vector2();
        touch = new Vector2();
        temp = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
        setBottom(worldBounds.getBottom() + 0.15f);
    }

    @Override
    public void touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        v.set(touch.x, 0).setLength(V);
    }

    @Override
    public void update(float delta) {

            pos.add(v);
    }
}


