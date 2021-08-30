package ru.elenakuropatkina.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.elenakuropatkina.base.Sprite;
import ru.elenakuropatkina.math.Rect;

public class Background extends Sprite {

    private final Vector2 v = new Vector2(0, -0.1f);
    private Rect worldBounds;

    public Background(Texture region) {
        super(new TextureRegion(region));
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(2f);
        this.pos.set(worldBounds.pos);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (getBottom() + getHalfHeight() < worldBounds.getBottom())  {
            setBottom(worldBounds.getTop() - getHalfHeight());
        }
    }
}
