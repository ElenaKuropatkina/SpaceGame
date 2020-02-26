package ru.elenakuropatkina.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.elenakuropatkina.base.Sprite;
import ru.elenakuropatkina.math.Rect;

public class Logo extends Sprite {

    private Vector2 touch, v, temp;
    public static final float V = 0.001f;

    public Logo(Texture region) {

        super(new TextureRegion(region));
        v = new Vector2();
        touch = new Vector2();
        temp = new Vector2();

    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
    }

    @Override
    public void touchDown(Vector2 touch, int pointer, int button) {
        this.touch.set(touch);
        v.set(touch.sub(pos)).setLength(V);
        System.out.println(v);

    }

    @Override
    public void update(float delta) {
        temp.set(touch);
        if(temp.sub(pos).len() > V){
            pos.add(v);
        } else
            pos.set(touch);


    }


}

