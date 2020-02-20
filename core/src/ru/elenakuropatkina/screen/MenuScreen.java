package ru.elenakuropatkina.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.elenakuropatkina.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Vector2 touch, temp, v, pos, direction;
    private static final Vector2 V = new Vector2(1f, 1f);

    @Override
    public void show() {

        super.show();
        img = new Texture("badlogic.jpg");
        touch = new Vector2();
        temp = new Vector2();
        v = new Vector2();
        pos = new Vector2();
        direction = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.5f, 0.9f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (Math.round(pos.x) != touch.x & Math.round(pos.y) != touch.y) {
            pos.add(v);
        }
        batch.begin();
        batch.draw(img, pos.x, pos.y);
        batch.end();
    }


    @Override
    public void dispose() {
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        super.touchDown(screenX, screenY, pointer, button);
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        temp.set(touch);
        v.set(V);
        direction.set((temp.sub(pos)).nor());
        v.scl(direction);

        return false;
    }

}


