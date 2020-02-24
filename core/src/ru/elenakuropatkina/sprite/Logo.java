package ru.elenakuropatkina.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.elenakuropatkina.base.Sprite;
import ru.elenakuropatkina.math.Rect;

public class Logo extends Sprite {

    Vector2 v;

    public Logo(Texture region) {

        super(new TextureRegion(region));
        this.v = new Vector2(0, 0.001f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.2f);
        this.pos.set(worldBounds.pos);
    }
    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        update(1f);
    }

    @Override
    public void touchDown(Vector2 touch, int pointer, int button) {
        super.touchDown(touch, pointer, button);

    }

    @Override
    public void update(float delta) {
        pos.add(v);
    }


}

